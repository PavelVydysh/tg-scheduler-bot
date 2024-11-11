package org.example.infrastructure.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RedisHash(PollHash.POLL_HASH_NAME)
public class PollHash {

    public static final String POLL_HASH_NAME = "PollHash";
    public static final String IDENTIFIER_SEPARATOR = ":";

    private String pollId;

    private String title;

    private Long tgChatId;

    private Long tgUserId;

    private Boolean isAnonymous;

    private Boolean isHeaderDateTime;

    private List<AvailableAnswerHash> availableAnswerHashes;

    public PollHash(Long tgUserId, Long tgChatId, String title, Boolean isAnonymous, Boolean isHeaderDateTime,
                    List<AvailableAnswerHash> availableAnswerHashes) {
        this.setPollId(tgUserId + IDENTIFIER_SEPARATOR + tgChatId);
        this.setTitle(title);
        this.setTgUserId(tgUserId);
        this.setTgChatId(tgChatId);
        this.setIsAnonymous(isAnonymous);
        this.setIsHeaderDateTime(isHeaderDateTime);
        this.availableAnswerHashes = availableAnswerHashes;
    }

    public PollHash(Long tgUserId, Long tgChatId) {
        this(tgUserId, tgChatId, null, true, false, null);
    }

    public List<AvailableAnswerHash> getAvailableAnswerHashes() {
        if (ObjectUtils.isEmpty(availableAnswerHashes)) {
            availableAnswerHashes = new ArrayList<>();
        }

        return availableAnswerHashes;
    }

}
