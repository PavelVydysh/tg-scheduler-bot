package org.example.api.command;

import org.example.api.ScheduleBot;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class InfoCommand extends Command{

    private final static String MESSAGE = "Инфо";

    public InfoCommand(ScheduleBot bot) {
        super(bot);
    }

    @Override
    void handle(Update update, String calledPattern, String currentState){
        SendMessage messageToSend = SendMessage
                .builder()
                .chatId(update.getMessage().getChatId())
                .text(MESSAGE)
                .build();
        bot.execute(messageToSend);
    }
}
