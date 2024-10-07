package org.example.api.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;

@Component
@Slf4j
public class CommandRouter {

    private String commandPrefix = "/";

    private Map<String, Command> commands;

    public CommandRouter(@Qualifier(value = "commands") Map<String, Command> commands) {
        this.commands = commands;
    }

    public void handle(Update update) {
        String commandFromUpdate = update.getMessage().getText();
        if (!update.getMessage().getText().startsWith(commandPrefix)) {
            return;
        }

        String command = StringUtils.delete(commandFromUpdate, commandPrefix);
        Command handler = commands.get(command);
        if (!ObjectUtils.isEmpty(handler)) {
            try {
                handler.handle(update);
            } catch (TelegramApiException e) {
                throw new RuntimeException();
            }
        }
    }

}
