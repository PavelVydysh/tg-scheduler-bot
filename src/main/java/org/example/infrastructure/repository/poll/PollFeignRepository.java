package org.example.infrastructure.repository.poll;

import org.example.domain.model.poll.Poll;
import org.example.domain.repository.PollRepository;
import org.example.infrastructure.converter.PollClientConverter;
import org.example.infrastructure.feign.dto.PollRequestClientDto;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PollFeignRepository implements PollRepository {

    @Override
    public void save(Poll poll) {
        PollRequestClientDto pollRequestClientDto = PollClientConverter.toPollRequestClientDto(poll);
        System.out.println(pollRequestClientDto);
    }

    @Override
    public Optional<Poll> findById(Long tgUserId, Long tgChatId) {
        return Optional.empty();
    }

    @Override
    public void removePollById(Long tgUserId, Long tgChatId) {

    }

}
