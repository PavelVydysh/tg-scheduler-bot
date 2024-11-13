package org.example.domain.repository;

import org.example.domain.model.poll.Poll;

import java.util.Optional;

public interface PollRepository {

    void save(Poll poll);

    Optional<Poll> findById(Long tgUserId, Long tgChatId);

    void removePollById(Long tgUserId, Long tgChatId);

}
