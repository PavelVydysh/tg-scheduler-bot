package org.example.infrastructure.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@RedisHash(value = UserSessionHash.HASH_NAME)
public class UserSessionHash {

    public static final String HASH_NAME = "UserSession";

    @Id
    private Long userId;

    private String command;

}
