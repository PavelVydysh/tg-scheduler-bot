package org.example.domain.repository;

import org.example.domain.model.State;

import java.util.Optional;

public interface StateRepository {

    Optional<State> findState(Long userId, Long chatId);

    void createState(State state);

    void updateState(State state);

    void removeState(Long userId, Long chatId);

}
