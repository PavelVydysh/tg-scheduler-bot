package org.example.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.model.State;
import org.example.domain.model.poll.Poll;
import org.example.domain.repository.PollRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PollService {

    private final PollRepository pollRepository;

    public void savePoll(Poll poll) {
        pollRepository.save(poll);
    }

    public Poll findPoll(Long tgUserId, Long tgChatId) {
        return pollRepository.findById(tgUserId, tgChatId).orElseThrow(RuntimeException::new);
    }

}