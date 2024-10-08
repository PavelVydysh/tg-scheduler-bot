package org.example.api.converter;

import org.example.domain.model.State;

public class StateConverter {

    public static State toState(Long userId, Long chatId, String command, String stateValue) {
        State state = new State();

        state.setUserId(userId);
        state.setChatId(chatId);
        state.setCommand(command);
        state.setStateValue(stateValue);

        return state;
    }

}
