package org.example.api.command;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.model.State;
import org.example.domain.model.UserSession;
import org.example.domain.service.UserSessionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class CommandRouter {

    private String commandPrefix = "/";

    private Map<String, Command> commands;

    private UserSessionService userSessionService;

    public CommandRouter(@Qualifier(value = "commands") Map<String, Command> commands,
                         UserSessionService userSessionService) {
        this.commands = commands;
        this.userSessionService = userSessionService;
    }

    public void handle(Update update) {
        System.out.println(commands);
        if (update.hasMessage()) {
            Message message = update.getMessage();
            Long userId = message.getFrom().getId();
            log.info("Новое сообщение: {}", message.getText());

            Optional<UserSession> optionalUserSession = userSessionService.findUserSessionByUserId(userId);
            if (optionalUserSession.isPresent()) {
                UserSession currentSession = optionalUserSession.get();
                log.info("Статус {}", currentSession);
                Command command = commands.get(currentSession.getCommand());
                if (!ObjectUtils.isEmpty(command) && command.supports(update)) {
                    command.execute(update);
                }
            } else {
                String commandFromUpdate = message.getText();
                if (!commandFromUpdate.startsWith(commandPrefix)) {
                    return;
                }
                log.info("статус null");
                String commandPattern = StringUtils.delete(commandFromUpdate, commandPrefix);
                Command command = commands.get(commandPattern);
                if (!ObjectUtils.isEmpty(command) && command.supports(update)) {
                    System.out.println(command.getAllowedChatTypes());
                    command.execute(update);
                }
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            Optional<State> optionalState = stateService.findStateByUserIdAnsChatId(callbackQuery.getFrom().getId(),
                    callbackQuery.getMessage().getChatId());
            if (optionalState.isPresent()) {
//                State currentState = optionalState.get();
//                CommandWithState handler = commands.get(currentState.getCommand());
//                handler.handleWithState(update, optionalState.get());
            }
        }
    }

}
