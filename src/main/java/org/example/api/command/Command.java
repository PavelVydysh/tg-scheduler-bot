package org.example.api.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.api.Bot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public abstract class Command {

    protected final Bot bot;

    protected String pattern;

    protected List<String> allowedChatTypes;

    public abstract void execute(Update update);

    public abstract Boolean supports(Update update);

}
