package org.example.api.command;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.model.State;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class CommandRouter {

    private String commandPrefix = "/";

    private Map<String, Command> commands;

    private final UserStateManager userStateManager;

    public CommandRouter(@Qualifier(value = "commands") Map<String, Command> commands,
                         UserStateManager userStateManager) {
        this.commands = commands;
        this.userStateManager = userStateManager;
    }

    public void handle(Update update) {
        if(update.hasMessage()) {
            Optional<State> optionalState = userStateManager.manage(update.getMessage());


        }
        State currentState = null;
        //проверка на наличие состояния
        Optional<State> optionalState = userStateManager.manage(update.getMessage());
        currentState = optionalState.orElse(
                new State(update.getMessage().getFrom().getId(),
                        update.getMessage().getChatId(),
                        command,
                        null)
        );

        if(optionalState.isPresent()) {
            log.info("статус:{}", optionalState.get());
        } else {
            log.info("нет статуса");
        }

        String commandFromUpdate = update.getMessage().getText();
        if (!commandFromUpdate.startsWith(commandPrefix) && optionalState.isPresent()) {
            return;
        }

        String command = StringUtils.delete(commandFromUpdate, commandPrefix);
        Command handler = commands.get(command);
        if (!ObjectUtils.isEmpty(handler)) {
            try {
                handler.handle(update, currentState);
            } catch (TelegramApiException e) {
                throw new RuntimeException();
            }
        }
    }

}
