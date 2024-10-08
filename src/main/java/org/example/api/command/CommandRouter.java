package org.example.api.command;

import lombok.RequiredArgsConstructor;
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
        //проверка на наличие состояния
        Optional<State> optionalState = userStateManager.manage(update.getMessage());
        if(optionalState.isPresent()) {
            log.info("статус");
        } else {
            log.info("нет статуса");
        }

        String commandFromUpdate = update.getMessage().getText();
        if (!update.getMessage().getText().startsWith(commandPrefix)) {
            return;
        }

        String command = StringUtils.delete(commandFromUpdate, commandPrefix);
        Command handler = commands.get(command);
        if (!ObjectUtils.isEmpty(handler)) {
            try {
                handler.handle(update, command);
            } catch (TelegramApiException e) {
                throw new RuntimeException();
            }
        }
    }

}
