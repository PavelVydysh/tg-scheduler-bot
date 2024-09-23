package org.example.api.command;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.api.ScheduleBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@RequiredArgsConstructor
public abstract class Command {

    protected final ScheduleBot bot;

    abstract void handle(Update update) throws TelegramApiException;

}
