package org.example.domain.repository;

import org.example.domain.model.poll.Poll;

import java.util.Optional;

public interface PollRepository {

    void save(Poll poll);

    void update(Poll poll);

    Optional<Poll> findById(Long tgUserId, Long tgChatId);

    Optional<Poll> findByTgId(Long id);

    void removePollById(String pollId);

}
