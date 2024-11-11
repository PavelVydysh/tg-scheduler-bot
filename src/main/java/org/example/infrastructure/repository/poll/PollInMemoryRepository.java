package org.example.infrastructure.repository.poll;

import org.example.domain.model.poll.Poll;
import org.example.domain.repository.PollRepository;
import org.example.infrastructure.converter.PollHashConverter;
import org.example.infrastructure.entity.PollHash;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Profile("memory")
@Repository
public class PollInMemoryRepository implements PollRepository {

    private List<PollHash> pollHashes = new ArrayList<>();

    @Override
    public void save(Poll poll) {
        PollHash pollHash = PollHashConverter.toPollHash(poll);
        removePollById(poll.getTgUserId(), poll.getTgChatId());
        pollHashes.add(pollHash);
    }

    @Override
    public Optional<Poll> findById(Long tgUserId, Long tgChatId) {
        PollHash currentPollHash = PollHashConverter.toPollHash(tgUserId, tgChatId);
        PollHash foundedPoll = pollHashes.stream().filter(ph -> ph.getPollId().equals(currentPollHash.getPollId())).findFirst().orElse(null);

        return Optional.ofNullable(PollHashConverter.toPoll(foundedPoll));
    }

    @Override
    public void removePollById(Long tgUserId, Long tgChatId) {
        PollHash currentPollHash = PollHashConverter.toPollHash(tgUserId, tgChatId);
        pollHashes.removeIf(ph -> ph.getPollId().equals(currentPollHash.getPollId()));
    }

}
