package org.example.infrastructure.converter;

import org.example.domain.model.State;
import org.example.infrastructure.entity.redis.StateHash;

public class StateHashConverter {

    public static StateHash toStateHash(State state) {
        return new StateHash(state.getUserId(),
                state.getChatId(),
                state.getCommand(),
                state.getStateValue());
    }

    public static StateHash toStateHash(Long userId, Long chatId) {
        return new StateHash(userId,
                chatId);
    }

    public static State toState(StateHash stateHash) {
        State state = new State();
        state.setUserId(stateHash.getUserId());
        state.setChatId(stateHash.getChatId());
        state.setCommand(stateHash.getCommand());
        state.setStateValue(stateHash.getStateValue());

        return state;
    }

}
