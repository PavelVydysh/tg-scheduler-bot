package org.example.infrastructure.repository.poll;

import org.example.domain.model.poll.Poll;
import org.example.domain.repository.PollRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Profile("redis")
@Repository("shortTermRepository")
public class PollRedisRepository implements PollRepository {
    @Override
    public void save(Poll poll) {

    }

    @Override
    public void update(Poll poll) {

    }

    @Override
    public Optional<Poll> findById(Long tgUserId, Long tgChatId) {
        return null;
    }

    @Override
    public Optional<Poll> findByTgId(Long id) {
        return null;
    }

    @Override
    public void removePollById(String pollId) {

    }

}
