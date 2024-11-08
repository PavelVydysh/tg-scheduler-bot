package org.example.infrastructure.repository.state;

import org.example.domain.model.State;
import org.example.domain.repository.StateRepository;
import org.example.infrastructure.converter.StateHashConverter;
import org.example.infrastructure.entity.StateHash;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("memory")
public class StateInMemoryRepository implements StateRepository {

    List<StateHash> stateHashes = new ArrayList<>();

    @Override
    public Optional<State> findState(Long userId, Long chatId) {
        StateHash currentStateHash = StateHashConverter.toStateHash(userId, chatId);

        StateHash foundedStateHash = stateHashes.stream().filter(s -> s.getId().equals(currentStateHash.getId())).findFirst().orElse(null);

        return Optional.ofNullable(
                StateHashConverter.toState(foundedStateHash)
        );
    }

    @Override
    public void createState(State state) {
        StateHash stateHash = StateHashConverter.toStateHash(state);
        stateHashes.add(stateHash);
    }

    @Override
    public void updateState(State state) {
        StateHash stateHash = StateHashConverter.toStateHash(state);
        stateHashes.removeIf(s -> s.getId().equals(stateHash.getId()));
        stateHashes.add(stateHash);
    }

    @Override
    public void removeState(Long userId, Long chatId) {
        StateHash stateHash = StateHashConverter.toStateHash(userId, chatId);
        stateHashes.removeIf(s -> s.getId().equals(stateHash.getId()));
    }

}
