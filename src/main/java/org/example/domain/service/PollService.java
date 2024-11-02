package org.example.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.model.State;
import org.example.domain.model.poll.Poll;
import org.example.domain.repository.PollRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PollService {

    private final PollRepository shortTermRepository;
    private final PollRepository longTermRepository;

    public PollService(@Qualifier("longTermRepository") PollRepository longTermRepository,
                       @Qualifier("shortTermRepository") PollRepository shortTermRepository) {
        this.shortTermRepository = shortTermRepository;
        this.longTermRepository = longTermRepository;
    }

    public void savePoll(Poll poll, State state) {

    }

}
