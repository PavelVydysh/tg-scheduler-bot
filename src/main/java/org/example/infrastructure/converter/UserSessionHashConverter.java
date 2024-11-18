package org.example.infrastructure.converter;

import org.example.domain.model.UserSession;
import org.example.infrastructure.entity.UserSessionHash;
import org.springframework.util.ObjectUtils;

public class UserSessionHashConverter {

    public static UserSessionHash toUserSessionHash(UserSession userSession) {
        if (ObjectUtils.isEmpty(userSession)) {
            return null;
        }

        UserSessionHash userSessionHash = new UserSessionHash();
        userSessionHash.setUserId(userSession.getUserId());
        userSessionHash.setCommand(userSession.getCommand());

        return userSessionHash;
    }

    public static UserSession touserSession(UserSessionHash userSessionHash) {
        if (ObjectUtils.isEmpty(userSessionHash)) {
            return null;
        }

        UserSession userSession = new UserSession();
        userSession.setUserId(userSessionHash.getUserId());
        userSession.setCommand(userSessionHash.getCommand());

        return userSession;
    }

}
