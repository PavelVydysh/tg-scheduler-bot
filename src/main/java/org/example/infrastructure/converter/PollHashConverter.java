package org.example.infrastructure.converter;

import org.example.domain.model.poll.Poll;
import org.example.infrastructure.entity.PollHash;
import org.springframework.util.ObjectUtils;

public class PollHashConverter {

    public static PollHash toPollHash(Poll poll) {
        if (ObjectUtils.isEmpty(poll)) {
            return null;
        }

        return new PollHash(
                poll.getTgUserId(),
                poll.getTgChatId(),
                poll.getTitle(),
                AvailableAnswerHashConverter.toListAvailableAnswerHash(poll.getAvailableAnswers())
        );
    }

    public static PollHash toPollHash(Long tgUserId, Long tgChatId) {
        if (ObjectUtils.isEmpty(tgUserId) || ObjectUtils.isEmpty(tgChatId)) {
            return null;
        }

        return new PollHash(tgUserId, tgChatId);
    }

    public static Poll toPoll(PollHash pollHash) {
        if (ObjectUtils.isEmpty(pollHash)) {
            return null;
        }

        Poll poll = new Poll();
        poll.setPollId(pollHash.getPollId());
        poll.setTitle(pollHash.getTitle());
        poll.setTgUserId(pollHash.getTgUserId());
        poll.setTgChatId(pollHash.getTgChatId());
        poll.setAvailableAnswers(
                AvailableAnswerHashConverter
                        .toListAvailableAnswers(pollHash.getAvailableAnswerHashes())
        );

        return poll;
    }

}
