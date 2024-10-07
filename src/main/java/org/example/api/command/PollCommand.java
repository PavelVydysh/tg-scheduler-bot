package org.example.api.command;

import org.example.api.ScheduleBot;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class PollCommand extends Command{

    public PollCommand(ScheduleBot bot) {
        super(bot);
    }

    @Override
    void handle(Update update) throws TelegramApiException {

    }

}
