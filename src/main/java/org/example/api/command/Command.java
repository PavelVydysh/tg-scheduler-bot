package org.example.api.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.api.ScheduleBot;
import org.example.domain.model.State;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public abstract class Command {

    protected final ScheduleBot bot;

    protected String pattern;

    protected List<String> chatTypes;

    public abstract void handle(Update update, String calledPattern);

}
