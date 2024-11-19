package org.example.api.command;

import lombok.extern.slf4j.Slf4j;
import org.example.api.ScheduleBot;
import org.example.api.converter.StateConverter;
import org.example.api.converter.poll.AvailableAnswerConverter;
import org.example.api.converter.poll.PollConverter;
import org.example.domain.model.State;
import org.example.domain.model.enums.PollState;
import org.example.domain.model.poll.AvailableAnswer;
import org.example.domain.model.poll.Poll;
import org.example.domain.service.PollService;
import org.example.domain.service.StateService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.List;

@Component
@Slf4j
public class PollCommand extends Command {

    private final static String INPUT_POLL_TITLE_MESSAGE = "Укажите название опроса:";
    private final static String POLL_TITLE_SAVE_MESSAGE = "Заголовок \"%s\" сохранен";
    private final static String SET_AVAILABLE_ANSWER_BUTTON_TEXT = "Добавить вариант ответа";
    private final static String SET_AVAILABLE_ANSWER_BUTTON_CALLBACK_DATA = "set_available_answer";
    private final static String COMPLETE_BUTTON_TEXT = "Завершить";
    private final static String COMPLETE_BUTTON_CALLBACK_DATA = "complete_poll";
    private final static String INPUT_AVAILABLE_ANSWER_MESSAGE = "Укажите вариант ответа:";
    private final static String COMPLETE_MESSAGE = "Почти закончили!";
    private final static String AVAILABLE_ANSWER_SAVE_MESSAGE = "Вариант ответа \"%s\" сохранен";
    private final static String CHOOSING_ACTION_MESSAGE = "Что вы хотите сделать?";
    private final static String OPTIONS_MESSAGE = "Настройте опрос:";
    private final static String ACTIVE_EMOJI = "✅";
    private final static String INACTIVE_EMOJI = "\uD83D\uDEAB";
    public static final String IS_ANONYMOUS_BUTTON_TEXT = "\uD83D\uDC64Анонимный: ";
    public static final String IS_DATETIME_BUTTON_TEXT = "\uD83D\uDD52Дата в заголовке: ";
    public static final String CONFIRM_BUTTON_TEXT = "Подтвердить";
    public static final String POLL_BUILD_SUCCESS = "Опросник успешно сформирован";
    public static final String IS_ANONYMOUS_BUTTON_CALLBACK = "is_anonymous";
    public static final String IS_DATETIME_BUTTON_CALLBACK = "is_header_datetime";
    public static final String CONFIRM_BUTTON_CALLBACK = "confirm";

    private final StateService stateService;

    private final PollService pollService;

    public PollCommand(ScheduleBot bot,
                       StateService stateService,
                       PollService pollService) {
        super(bot);
        this.stateService = stateService;
        this.pollService = pollService;
    }

    @Override
    public void execute(Update update) {
        Message message = update.getMessage();
        State state = StateConverter.toState(message.getFrom().getId(),
                message.getChatId(),
                calledPattern,
                PollState.INPUT_TITLE.name());

        stateService.createState(state);

        SendMessage messageToSend = SendMessage
                .builder()
                .chatId(update.getMessage().getChatId())
                .text(INPUT_POLL_TITLE_MESSAGE)
                .build();
        bot.execute(messageToSend);
    }

    @Override
    public Boolean supports(Update update) {
        return allowedChatTypes.contains(update.getMessage().getChat().getType());
    }

    @Override
    public void handleWithState(Update update, State state) {
        PollState pollState = PollState.valueOf(state.getStateValue());
        switch (pollState) {
            case INPUT_TITLE -> {
                executeInputTitleState(update, state);
            }
            case CHOOSING_ACTION -> {
                executeChoosingActionState(update, state);
            }
            case INPUT_AVAILABLE_ANSWER -> {
                executeInputAvailableAnswerState(update, state);
            }
            case SET_OPTIONS -> {
                executeSetOptionsState(update, state);
            }
        }
    }

    /**
     * Выполнение состояния выбора действия(ввод варианта/завершение)
     */
    private void executeChoosingActionState(Update update, State state) {
        if (!update.hasCallbackQuery()) {
            return;
        }

        CallbackQuery callbackQuery = update.getCallbackQuery();

        switch (callbackQuery.getData()) {
            case SET_AVAILABLE_ANSWER_BUTTON_CALLBACK_DATA -> {
                executeSetAvailableAnswerProcess(update, state);
            }
            case COMPLETE_BUTTON_CALLBACK_DATA -> {
                executeCompleteConstructPollProcess(update, state);
            }
        }
    }

    private void executeSetOptionsState(Update update, State state) {
        if (!update.hasCallbackQuery()) {
            return;
        }

        CallbackQuery callbackQuery = update.getCallbackQuery();
        Long tgUserId = callbackQuery.getFrom().getId();
        Long tgChatId = callbackQuery.getMessage().getChatId();

        switch (callbackQuery.getData()) {
            case IS_ANONYMOUS_BUTTON_CALLBACK -> {
                Poll poll = pollService.findPoll(tgUserId, tgChatId);
                poll.setIsAnonymous(!poll.getIsAnonymous());
                pollService.savePoll(poll);

                InlineKeyboardMarkup markup = constructOptionsMarkup(poll);
                EditMessageReplyMarkup updatedMarkup = constructUpdateMarkup(callbackQuery, markup);

                bot.execute(updatedMarkup);
            }
            case IS_DATETIME_BUTTON_CALLBACK -> {
                Poll poll = pollService.findPoll(tgUserId, tgChatId);
                poll.setIsHeaderDateTime(!poll.getIsHeaderDateTime());
                pollService.savePoll(poll);

                InlineKeyboardMarkup markup = constructOptionsMarkup(poll);
                EditMessageReplyMarkup updatedMarkup = constructUpdateMarkup(callbackQuery, markup);

                bot.execute(updatedMarkup);
            }
            case CONFIRM_BUTTON_CALLBACK -> {
                Poll poll = pollService.findPoll(tgUserId, tgChatId);

                SendMessage messageToSend = SendMessage
                        .builder()
                        .chatId(tgChatId)
                        .text(POLL_BUILD_SUCCESS)
                        .build();
                bot.execute(messageToSend);

                String pollInfo = "Настройки:\n" +
                        "Анонимный: " + identifyEmoji(poll.getIsAnonymous()) +
                        "\nДата в заголовке: " + identifyEmoji(poll.getIsHeaderDateTime()) +
                        "\n\nИтоговый опрос:\n" +
                        "\"" + poll.getTitle() + "\"" +
                        "\n";

                for(AvailableAnswer availableAnswer : poll.getAvailableAnswers()) {
                    pollInfo += availableAnswer.getPosition() + ") " + availableAnswer.getTitle() + "\n";
                }

                SendMessage pollInfoMessage = SendMessage
                        .builder()
                        .chatId(tgChatId)
                        .text(pollInfo)
                        .build();
                bot.execute(pollInfoMessage);

                stateService.removeState(state.getUserId(), state.getChatId());
            }
        }

    }

    /**
     * Выполнение состояния ввода заголовка
     * Переход в состояние выбора действия(Ввод варианта/завершение)
     */
    private void executeInputTitleState(Update update, State state) {
        if (!update.hasMessage()) {
            return;
        }

        Message message = update.getMessage();

        Poll poll = PollConverter.toPollWithoutAvailableAnswers(message);
        pollService.savePoll(poll);

        State actualState = StateConverter.toState(state.getUserId(),
                state.getChatId(),
                state.getCommand(),
                PollState.CHOOSING_ACTION.name());

        stateService.updateState(actualState);

        SendMessage messageToSend = SendMessage
                .builder()
                .chatId(update.getMessage().getChatId())
                .text(String.format(POLL_TITLE_SAVE_MESSAGE, message.getText()))
                .build();
        bot.execute(messageToSend);

        SendMessage menuInfo = constructChoosingStateSendMessage(update);
        bot.execute(menuInfo);
    }

    /**
     * Переход в состояние ввода варианта ответа
     */
    private void executeSetAvailableAnswerProcess(Update update, State state) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Long userId = state.getUserId();
        Long chatId = state.getChatId();

        State currentState = StateConverter.toState(userId, chatId, state.getCommand(),
                PollState.INPUT_AVAILABLE_ANSWER.name());

        stateService.updateState(currentState);

        SendMessage messageToSend = SendMessage
                .builder()
                .chatId(callbackQuery.getMessage().getChatId())
                .text(INPUT_AVAILABLE_ANSWER_MESSAGE)
                .build();
        bot.execute(messageToSend);
    }

    /**
     * Переход в состояние настройки опроса
     */
    private void executeCompleteConstructPollProcess(Update update, State state) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Long userId = state.getUserId();
        Long chatId = state.getChatId();

        Poll poll = pollService.findPoll(userId, chatId);

        SendMessage messageToSend = SendMessage
                .builder()
                .chatId(callbackQuery.getMessage().getChatId())
                .text(COMPLETE_MESSAGE)
                .build();
        bot.execute(messageToSend);

        SendMessage menuToSend = constructOptionsMessage(update, poll);
        bot.execute(menuToSend);

        State currentState = StateConverter.toState(userId, chatId, state.getCommand(),
                PollState.SET_OPTIONS.name());
        stateService.updateState(currentState);
    }

    /**
     * Выполнение состояния ввода варианта ответа
     */
    private void executeInputAvailableAnswerState(Update update, State state) {
        if (!update.hasMessage()) {
            return;
        }

        Message message = update.getMessage();

        Poll poll = pollService.findPoll(message.getFrom().getId(), message.getChatId());
        AvailableAnswer availableAnswer = AvailableAnswerConverter.toAvailableAnswer(poll, message);
        poll.addAvailableAnswer(availableAnswer);
        pollService.savePoll(poll);

        State currentState = StateConverter.toState(message.getFrom().getId(), message.getChatId(), state.getCommand(),
                PollState.CHOOSING_ACTION.name());
        stateService.updateState(currentState);

        SendMessage availableAnswerSavedMessage = SendMessage.builder()
                .chatId(message.getChatId())
                .text(String.format(AVAILABLE_ANSWER_SAVE_MESSAGE, message.getText()))
                .build();
        bot.execute(availableAnswerSavedMessage);

        SendMessage choosingMenuMessage = constructChoosingStateSendMessage(update);
        bot.execute(choosingMenuMessage);
    }

    /**
     * Формирование сообщения с меню выбора действия формирования опроса
     */
    private SendMessage constructChoosingStateSendMessage(Update update) {
        SendMessage message = SendMessage
                .builder()
                .chatId(update.getMessage().getChatId())
                .text(CHOOSING_ACTION_MESSAGE)
                .build();

        InlineKeyboardMarkup markup = constructChoosingStateMarkup();
        message.setReplyMarkup(markup);

        return message;
    }

    /**
     * Формирование меню выбора действия формирования опроса
     */
    private InlineKeyboardMarkup constructChoosingStateMarkup() {
        InlineKeyboardButton setAvailableAnswerButton = new InlineKeyboardButton(SET_AVAILABLE_ANSWER_BUTTON_TEXT);
        setAvailableAnswerButton.setCallbackData(SET_AVAILABLE_ANSWER_BUTTON_CALLBACK_DATA);

        InlineKeyboardButton completeButton = new InlineKeyboardButton(COMPLETE_BUTTON_TEXT);
        completeButton.setCallbackData(COMPLETE_BUTTON_CALLBACK_DATA);

        InlineKeyboardRow firstRow = new InlineKeyboardRow();
        firstRow.add(setAvailableAnswerButton);

        InlineKeyboardRow secondRow = new InlineKeyboardRow();
        secondRow.add(completeButton);

        return new InlineKeyboardMarkup(
                List.of(firstRow, secondRow)
        );
    }

    /**
     * Формирование сообщения настройки опроса с меню настроек
     */
    private SendMessage constructOptionsMessage(Update update, Poll poll) {
        SendMessage menuToSend = SendMessage
                .builder()
                .chatId(update.getCallbackQuery().getMessage().getChatId())
                .text(OPTIONS_MESSAGE)
                .build();
        menuToSend.setReplyMarkup(constructOptionsMarkup(poll));

        return menuToSend;
    }

    /**
     * Формирование меню настройки опроса
     */
    private InlineKeyboardMarkup constructOptionsMarkup(Poll poll) {
        String isAnonymousButtonText = IS_ANONYMOUS_BUTTON_TEXT + identifyEmoji(poll.getIsAnonymous());

        InlineKeyboardButton isAnonymousButton = new InlineKeyboardButton(isAnonymousButtonText);
        isAnonymousButton.setCallbackData(IS_ANONYMOUS_BUTTON_CALLBACK);

        String isHeaderDateTimeButtonText = IS_DATETIME_BUTTON_TEXT + identifyEmoji(poll.getIsHeaderDateTime());

        InlineKeyboardButton isDateTimeButton = new InlineKeyboardButton(isHeaderDateTimeButtonText);
        isDateTimeButton.setCallbackData(IS_DATETIME_BUTTON_CALLBACK);

        InlineKeyboardRow firstRow = new InlineKeyboardRow();
        firstRow.add(isAnonymousButton);
        firstRow.add(isDateTimeButton);

        InlineKeyboardButton confirmButton = new InlineKeyboardButton(CONFIRM_BUTTON_TEXT);
        confirmButton.setCallbackData(CONFIRM_BUTTON_CALLBACK);

        InlineKeyboardRow secondRow = new InlineKeyboardRow();
        secondRow.add(confirmButton);

        return new InlineKeyboardMarkup(
                List.of(firstRow, secondRow)
        );
    }

    public EditMessageReplyMarkup constructUpdateMarkup(CallbackQuery callbackQuery, InlineKeyboardMarkup updatedMarkup) {
        EditMessageReplyMarkup editMarkup = new EditMessageReplyMarkup();
        editMarkup.setChatId(callbackQuery.getMessage().getChatId());
        editMarkup.setMessageId(callbackQuery.getMessage().getMessageId());
        editMarkup.setReplyMarkup(updatedMarkup);

        return editMarkup;
    }

    private String identifyEmoji(Boolean condition) {
        if (condition) {
            return ACTIVE_EMOJI;
        }
        return INACTIVE_EMOJI;
    }

}
