package org.example.infrastructure.repository.poll;

import org.example.domain.model.poll.Poll;
import org.example.domain.repository.PollRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository("shortTermRepository")
public class PollRedisRepository implements PollRepository {
    @Override
    public void save(Poll poll) {

    }

    @Override
    public void update(Poll poll) {

    }

    @Override
    public Poll findById(UUID id) {
        return null;
    }

    @Override
    public Poll findByTgId(Long id) {
        return null;
    }
}
