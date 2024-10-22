package org.example.api.command;

import lombok.extern.slf4j.Slf4j;
import org.example.api.ScheduleBot;
import org.example.api.converter.StateConverter;
import org.example.domain.model.State;
import org.example.domain.service.StateService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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
    private final static String COMPLETE_MESSAGE = "Опрос сформирован успешно!";
    private final static String AVAILABLE_ANSWER_SAVE_MESSAGE = "Вариант ответа \"%s\" сохранен";
    private final static String CHOOSING_ACTION_MESSAGE = "Что вы хотите сделать?";

    private final StateService stateService;

    public PollCommand(ScheduleBot bot,
                       StateService stateService) {
        super(bot);
        this.stateService = stateService;
    }

    @Override
    void handle(Update update, String calledPattern, String currentState) {
        log.info("State is {}", currentState);

        if(!StringUtils.hasText(currentState)) {
            executeEmptyState(update, calledPattern);
        } else {
            PollState state = PollState.valueOf(currentState);
            switch (state) {
                case INPUT_TITLE -> {
                    executeInputTitleState(update, calledPattern);
                }
                case CHOOSING_ACTION -> {
                    executeChoosingActionState(update, calledPattern);
                }
                case INPUT_AVAILABLE_ANSWER -> {
                    executeInputAvailableAnswerState(update, calledPattern);
                }
            }
        }
    }

    @NotNull
    private static InlineKeyboardMarkup constructChoosingStateMarkup() {
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

    public SendMessage constructChoosingStateSendMessage(Update update) {
        SendMessage message = SendMessage
                .builder()
                .chatId(update.getMessage().getChatId())
                .text(CHOOSING_ACTION_MESSAGE)
                .build();

        InlineKeyboardMarkup markup = constructChoosingStateMarkup();
        message.setReplyMarkup(markup);

        return message;
    }

    private void executeEmptyState(Update update, String calledPattern) {
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

    private void executeInputTitleState(Update update, String calledPattern) {
        //TODO сохранение введенного заголовка в Redis
        if(!update.hasMessage()) {
            return;
        }

        Message message = update.getMessage();
        State actualState = StateConverter.toState(message.getFrom().getId(),
                message.getChatId(),
                calledPattern,
                PollState.CHOOSING_ACTION.name());

        stateService.updateState(message.getFrom().getId(), message.getChatId(), actualState);

        SendMessage messageToSend = SendMessage
                .builder()
                .chatId(update.getMessage().getChatId())
                .text(String.format(POLL_TITLE_SAVE_MESSAGE, message.getText()))
                .build();
        bot.execute(messageToSend);

        SendMessage menuInfo = constructChoosingStateSendMessage(update);
        bot.execute(menuInfo);
    }

    private void executeChoosingActionState(Update update, String calledPattern) {
        if(!update.hasCallbackQuery()) {
            return;
        }

        CallbackQuery callbackQuery = update.getCallbackQuery();
        Long userId = callbackQuery.getFrom().getId();
        Long chatId = callbackQuery.getMessage().getChatId();

        switch (callbackQuery.getData()) {
            case SET_AVAILABLE_ANSWER_BUTTON_CALLBACK_DATA -> {
                State state = StateConverter.toState(userId, chatId, calledPattern,
                        PollState.INPUT_AVAILABLE_ANSWER.name());

                stateService.updateState(userId, chatId, state);

                SendMessage messageToSend = SendMessage
                        .builder()
                        .chatId(callbackQuery.getMessage().getChatId())
                        .text(INPUT_AVAILABLE_ANSWER_MESSAGE)
                        .build();
                bot.execute(messageToSend);
            }
            case COMPLETE_BUTTON_CALLBACK_DATA -> {
                State state = StateConverter.toState(userId, chatId, calledPattern,
                        PollState.ACCEPT.name());

                stateService.updateState(userId, chatId, state);

                SendMessage messageToSend = SendMessage
                        .builder()
                        .chatId(callbackQuery.getMessage().getChatId())
                        .text(COMPLETE_MESSAGE)
                        .build();
                bot.execute(messageToSend);
            }
        }
    }

    public void executeInputAvailableAnswerState(Update update, String calledPattern) {
        if(!update.hasMessage()) {
            return;
        }

        //TODO логика сохранения варианта овтета в redis

        Message message = update.getMessage();

        State state = StateConverter.toState(message.getFrom().getId(), message.getChatId(), calledPattern,
                PollState.CHOOSING_ACTION.name());
        stateService.updateState(message.getFrom().getId(), message.getChatId(), state);

        String availableAnswer = message.getText();
        SendMessage availableAnswerSavedMessage = SendMessage.builder()
                .chatId(message.getChatId())
                .text(String.format(AVAILABLE_ANSWER_SAVE_MESSAGE, availableAnswer))
                .build();
        bot.execute(availableAnswerSavedMessage);

        SendMessage choosingMenuMessage = constructChoosingStateSendMessage(update);
        bot.execute(choosingMenuMessage);
    }

    private enum PollState {
        INPUT_TITLE,
        CHOOSING_ACTION,
        INPUT_AVAILABLE_ANSWER,
        ACCEPT
    }

}
