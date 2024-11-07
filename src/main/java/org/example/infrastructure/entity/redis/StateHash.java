package org.example.infrastructure.entity.redis;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor
@RedisHash(StateHash.STATE_HASH_NAME)
public class StateHash {

    public static final String STATE_HASH_NAME = "State";
    public static final String IDENTIFIER_SEPARATOR = ":";

    @Id
    private String id;

    private Long userId;

    private Long chatId;

    private String command;

    private String stateValue;

    public StateHash(Long userId, Long chatId, String command, String stateValue) {
        this.id = userId + IDENTIFIER_SEPARATOR + chatId;
        this.userId = userId;
        this.chatId = chatId;
        this.command = command;
        this.stateValue = stateValue;
    }

    public StateHash(Long userId, Long chatId) {
        this(userId, chatId, null, null);
    }
}
