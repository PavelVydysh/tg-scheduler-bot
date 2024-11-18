package org.example.infrastructure.repository.usersession;

import org.example.domain.model.UserSession;
import org.example.domain.repository.UserSessionRepository;
import org.example.infrastructure.converter.UserSessionHashConverter;
import org.example.infrastructure.entity.UserSessionHash;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserSessionInMemoryRepository implements UserSessionRepository {

    private final List<UserSessionHash> userSessionHashes = new ArrayList<>();

    @Override
    public void save(UserSession userSession) {
        UserSessionHash userSessionHash = UserSessionHashConverter.toUserSessionHash(userSession);
        userSessionHashes.removeIf(us -> us.getUserId().equals(userSessionHash.getUserId()));
        userSessionHashes.add(userSessionHash);
    }

    @Override
    public Optional<UserSession> findByUserId(Long userId) {
        Optional<UserSessionHash> foundedUsesSessionHashOptional = userSessionHashes.stream().filter(us -> us.getUserId().equals(userId)).findFirst();

        return foundedUsesSessionHashOptional.map(UserSessionHashConverter::touserSession);
    }

    @Override
    public void removeByUserId(Long userId) {
        userSessionHashes.removeIf(us -> us.getUserId().equals(userId));
    }
}
