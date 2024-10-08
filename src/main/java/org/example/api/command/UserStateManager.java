package org.example.api.command;

import lombok.RequiredArgsConstructor;
import org.example.domain.model.State;
import org.example.domain.service.StateService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserStateManager {

    private final StateService stateService;

    public Optional<State> manage(Message message) {
        return stateService.findStateByUserIdAnsChatId(message.getFrom().getId(), message.getChatId());
    }

}
