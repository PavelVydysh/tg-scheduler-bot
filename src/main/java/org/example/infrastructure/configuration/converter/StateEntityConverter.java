package org.example.infrastructure.configuration.converter;

import org.example.domain.model.State;
import org.example.infrastructure.configuration.entity.StateEntity;

public class StateEntityConverter {

    public static State toState(StateEntity stateEntity) {
        State state = new State();

        state.setUserId(stateEntity.getUserId());
        state.setChatId(stateEntity.getChatId());
        state.setCommand(stateEntity.getCommand());
        state.setStateValue(stateEntity.getStateValue());

        return state;
    }

    public static StateEntity toStateEntity(State state) {
        StateEntity stateEntity = new StateEntity();

        stateEntity.setChatId(state.getChatId());
        stateEntity.setUserId(state.getUserId());
        stateEntity.setCommand(state.getCommand());
        stateEntity.setStateValue(state.getStateValue());

        return stateEntity;
    }

}
