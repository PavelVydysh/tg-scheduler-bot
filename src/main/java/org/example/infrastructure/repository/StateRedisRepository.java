package org.example.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.model.State;
import org.example.domain.repository.StateRepository;
import org.example.infrastructure.converter.StateHashConverter;
import org.example.infrastructure.dao.redis.StateDao;
import org.example.infrastructure.entity.redis.StateHash;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class StateRedisRepository implements StateRepository {

    private final StateDao stateDao;

    @Override
    public Optional<State> findState(Long userId, Long chatId) {
        StateHash stateHash = StateHashConverter.toStateHash(userId, chatId);
        Optional<StateHash> foundedStateOptional = stateDao.findById(stateHash.getId());
        return foundedStateOptional.map(StateHashConverter::toState);
    }

    @Override
    public void createState(State state) {
        log.info("save save save");
        StateHash stateHash = StateHashConverter.toStateHash(state);
        stateDao.save(stateHash);
        log.info("after after after");
    }

    @Override
    public void updateState(State state) {
        StateHash stateHash = StateHashConverter.toStateHash(state);
        stateDao.deleteById(stateHash.getId());
        stateDao.save(stateHash);
    }

    @Override
    public void removeState(Long userId, Long chatId) {
        String stateId = userId + StateHash.IDENTIFIER_SEPARATOR + chatId;
        stateDao.deleteById(stateId);
    }
}
