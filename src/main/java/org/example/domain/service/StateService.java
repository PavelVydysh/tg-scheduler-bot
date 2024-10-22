package org.example.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.model.State;
import org.example.domain.repository.StateRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StateService {

    private final StateRepository stateRepository;

    public void createState(State state) {
        stateRepository.createState(state);
    }

    public Optional<State> findStateByUserIdAnsChatId(Long userId, Long chatId) {
        return stateRepository.findState(userId, chatId);
    }

    public void updateState(Long userId, Long chatId, State state) {
        stateRepository.updateState(userId,chatId, state);
    }

}
