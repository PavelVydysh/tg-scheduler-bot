package org.example.api.command;

import lombok.extern.slf4j.Slf4j;
import org.example.api.Bot;
import org.example.domain.service.PollService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

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

    private final PollService pollService;

    public PollCommand(Bot bot,
                       PollService pollService) {
        super(bot);
        this.pollService = pollService;
    }

    @Override
    public void execute(Update update) {

        SendMessage messageToSend = SendMessage
                .builder()
                .chatId(update.getMessage().getChatId())
                .text("Что вы хотите сделать?")
                .replyMarkup(buildKeyboardMarkup())
                .build();

        bot.execute(messageToSend);
    }

    @Override
    public Boolean supports(Update update) {
        return allowedChatTypes.contains(update.getMessage().getChat().getType());
    }

    private InlineKeyboardMarkup buildKeyboardMarkup() {

        InlineKeyboardButton showPollsButton = InlineKeyboardButton.builder()
                .text("Просмотреть список опросов")
                .callbackData("show_poll_list")
                .build();

        InlineKeyboardButton createPollButton = InlineKeyboardButton.builder()
                .text("Создать опрос")
                .webApp(WebAppInfo.builder()
                        .url("https://127.0.0.1:5500/?tgWebAppDebug=true")
                        .build())
                .build();

        InlineKeyboardRow firstRow = new InlineKeyboardRow(List.of(showPollsButton));
        InlineKeyboardRow secondRow = new InlineKeyboardRow(List.of(createPollButton));

        return new InlineKeyboardMarkup(
                List.of(firstRow,
                        secondRow)
        );

    }

}
