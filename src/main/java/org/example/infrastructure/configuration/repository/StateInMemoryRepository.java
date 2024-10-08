package org.example.infrastructure.configuration.repository;

import org.example.domain.model.State;
import org.example.domain.repository.StateRepository;
import org.example.infrastructure.configuration.converter.StateEntityConverter;
import org.example.infrastructure.configuration.entity.StateEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class StateInMemoryRepository implements StateRepository {

    List<StateEntity> stateEntities = new ArrayList<>();

    @Override
    public Optional<State> findState(Long userId, Long chatId) {
        for(StateEntity stateEntity : stateEntities) {
            if(stateEntity.getUserId().equals(userId) && stateEntity.getChatId().equals(chatId)) {
                return Optional.of(
                        StateEntityConverter.toState(stateEntity)
                );
            }
        }

        return Optional.empty();
    }

    @Override
    public void createState(State state) {
        StateEntity stateEntity = StateEntityConverter.toStateEntity(state);
        stateEntities.add(stateEntity);
    }
}
