package org.example.domain.repository;

import org.example.domain.model.UserSession;

import java.util.Optional;

public interface UserSessionRepository {

    void save(UserSession userSession);

    Optional<UserSession> findByUserId(Long userId);

    void removeByUserId(Long userId);

}
