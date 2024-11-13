package org.example.api.command;

import lombok.RequiredArgsConstructor;
import org.example.api.ScheduleBot;
import org.example.domain.model.State;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
public abstract class Command {

    protected final ScheduleBot bot;

    public abstract void handle(Update update, String calledPattern);

}
