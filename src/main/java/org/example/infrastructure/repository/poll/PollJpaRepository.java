package org.example.infrastructure.repository.poll;

import org.example.domain.model.poll.Poll;
import org.example.domain.repository.PollRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PollJpaRepository implements PollRepository {
    @Override
    public void savePoll(Poll poll) {

    }
}
