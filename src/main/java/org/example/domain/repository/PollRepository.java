package org.example.domain.repository;

import org.example.domain.model.poll.Poll;

import java.util.UUID;

public interface PollRepository {

    void save(Poll poll);

    void update(Poll poll);

    Poll findById(UUID id);

    Poll findByTgId(Long id);

}
