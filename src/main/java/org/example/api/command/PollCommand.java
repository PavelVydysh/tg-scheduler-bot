package org.example.api.command;

import org.example.api.ScheduleBot;
import org.example.api.converter.StateConverter;
import org.example.domain.model.State;
import org.example.domain.service.StateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collections;
import java.util.Objects;

@Component
public class PollCommand extends Command {

    private final static String INPUT_POLL_TITLE = "Укажите название опроса:";
    private static final Logger log = LoggerFactory.getLogger(PollCommand.class);

    private final StateService stateService;

    public PollCommand(ScheduleBot bot,
                       StateService stateService) {
        super(bot);
        this.stateService = stateService;
    }

    @Override
    void handle(Update update, State currentState) throws TelegramApiException {
        if(ObjectUtils.isEmpty(currentState)) {
            SendMessage messageToSend = SendMessage
                    .builder()
                    .chatId(update.getMessage().getChatId())
                    .text(INPUT_POLL_TITLE)
                    .build();
            bot.getTelegramClient().execute(messageToSend);

            Message message = update.getMessage();
            State state = StateConverter.toState(message.getFrom().getId(),
                    message.getChatId(),
                    currentState.getCommand(),
                    PollState.INPUT_TITLE.name());

            stateService.createState(state);
        } else {
            PollState currentStateValue = PollState.valueOf(currentState.getStateValue());

            switch (currentStateValue) {
                case INPUT_TITLE -> {
                    String title = update.getMessage().getText();
                    log.info("Заголовок опроса: {}", title);
                }
                case INPUT_AVAILABLE_ANSWER -> {
                    log.info(PollState.INPUT_AVAILABLE_ANSWER.name());
                }
                case ACCEPT -> {
                    log.info(PollState.ACCEPT.name());
                }
            }
        }
    }

    private enum PollState {
        INPUT_TITLE,
        INPUT_AVAILABLE_ANSWER,
        ACCEPT
    }

}
