package org.example.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.model.UserSession;
import org.example.domain.repository.UserSessionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSessionService {

    private final UserSessionRepository userSessionRepository;

    public void saveUserSession(UserSession userSession) {
        userSessionRepository.save(userSession);
    }

    public Optional<UserSession> findUserSessionByUserId(Long userId) {
        return userSessionRepository.findByUserId(userId);
    }

    public void removeUserSessionByUserId(Long userId) {
        userSessionRepository.removeByUserId(userId);
    }

}
