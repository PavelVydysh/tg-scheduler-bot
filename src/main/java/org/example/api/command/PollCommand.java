package org.example.api.command;

import org.example.api.ScheduleBot;
import org.example.api.converter.StateConverter;
import org.example.domain.model.State;
import org.example.domain.service.StateService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class PollCommand extends Command {

    private final static String INPUT_POLL_TITLE = "Укажите название опроса:";

    private final StateService stateService;

    public PollCommand(ScheduleBot bot,
                       StateService stateService) {
        super(bot);
        this.stateService = stateService;
    }

    @Override
    void handle(Update update, String calledPattern) throws TelegramApiException {
        Message message = update.getMessage();
        State state = StateConverter.toState(message.getFrom().getId(),
                message.getChatId(),
                calledPattern,
                PollState.INPUT_TITLE.name());

        stateService.createState(state);

        SendMessage messageToSend = SendMessage
                .builder()
                .chatId(update.getMessage().getChatId())
                .text(INPUT_POLL_TITLE)
                .build();
        bot.getTelegramClient().execute(messageToSend);
    }

    private enum PollState {
        INPUT_TITLE,
        INPUT_AVAILABLE_ANSWER,
        ACCEPT
    }

}
