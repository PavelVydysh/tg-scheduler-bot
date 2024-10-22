package org.example.domain.repository;

import org.example.domain.model.poll.Poll;

public interface PollRepository {

    void savePoll(Poll poll);

}
