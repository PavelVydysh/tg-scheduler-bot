package org.example.api.command;

import org.example.api.ScheduleBot;
import org.example.infrastructure.configuration.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class InfoCommand extends Command{

    @Value(Environment.COMMAND_INFO_MESSAGE_KEY)
    private String message;

    public InfoCommand(ScheduleBot bot) {
        super(bot);
    }

    @Override
    void handle(Update update) throws TelegramApiException {
        SendMessage messageToSend = SendMessage
                .builder()
                .chatId(update.getMessage().getChatId())
                .text(message)
                .build();
        bot.getTelegramClient().execute(messageToSend);
    }
}
