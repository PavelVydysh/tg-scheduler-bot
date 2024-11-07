package org.example.infrastructure.converter;

import org.example.domain.model.State;
import org.example.infrastructure.entity.redis.StateHash;
import org.springframework.util.ObjectUtils;

public class StateHashConverter {

    public static StateHash toStateHash(State state) {
        if (ObjectUtils.isEmpty(state)) {
            return null;
        }

        return new StateHash(state.getUserId(),
                state.getChatId(),
                state.getCommand(),
                state.getStateValue());
    }

    public static StateHash toStateHash(Long userId, Long chatId) {
        if (ObjectUtils.isEmpty(userId) || ObjectUtils.isEmpty(chatId)) {
            return null;
        }

        return new StateHash(userId,
                chatId);
    }

    public static State toState(StateHash stateHash) {
        if (ObjectUtils.isEmpty(stateHash)) {
            return null;
        }

        State state = new State();
        state.setUserId(stateHash.getUserId());
        state.setChatId(stateHash.getChatId());
        state.setCommand(stateHash.getCommand());
        state.setStateValue(stateHash.getStateValue());

        return state;
    }

}
