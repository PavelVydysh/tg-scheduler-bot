package org.example.api.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.model.State;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
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
            Message message = update.getMessage();
            log.info("Новое сообщение: {}", message.getText());
            Optional<State> optionalState = userStateManager.manage(message.getFrom().getId(), message.getChatId());
            if(optionalState.isPresent()) {
                State currentState = optionalState.get();
                log.info("Статус {}", currentState);
                Command handler = commands.get(currentState.getCommand());
                handler.handle(update, currentState.getCommand(), currentState.getStateValue());
            } else {
                String commandFromUpdate = message.getText();
                if (!commandFromUpdate.startsWith(commandPrefix)) {
                    return;
                }
                log.info("статус null");
                String command = StringUtils.delete(commandFromUpdate, commandPrefix);
                Command handler = commands.get(command);
                if (!ObjectUtils.isEmpty(handler)) {
                    handler.handle(update, command, null);
                }
            }
        } else if(update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            Optional<State> optionalState = userStateManager.manage(callbackQuery.getFrom().getId(), callbackQuery.getMessage().getChatId());
            if(optionalState.isPresent()) {
                State currentState = optionalState.get();
                Command handler = commands.get(currentState.getCommand());
                handler.handle(update, currentState.getCommand(), currentState.getStateValue());
            }
        }
    }

}
