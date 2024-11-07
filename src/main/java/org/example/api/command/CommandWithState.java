package org.example.api.command;

import org.example.api.ScheduleBot;
import org.example.domain.model.State;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class CommandWithState extends Command{

    public CommandWithState(ScheduleBot bot) {
        super(bot);
    }

    public abstract void handleWithState(Update update, State state);

}
