package org.example.infrastructure.converter;

import org.example.domain.model.poll.Poll;
import org.example.infrastructure.feign.dto.PollRequestClientDto;
import org.springframework.util.ObjectUtils;

public class PollClientConverter {

    public static PollRequestClientDto toPollRequestClientDto(Poll poll) {
        if (ObjectUtils.isEmpty(poll)) {
            return null;
        }

        PollRequestClientDto pollRequestClientDto = new PollRequestClientDto();
        pollRequestClientDto.setTitle(poll.getTitle());
        pollRequestClientDto.setAvailableAnswers(
                AvailableAnswerClientConverter
                        .toListAvailableAnswerRequestClientDto(poll.getAvailableAnswers())
        );

        return pollRequestClientDto;
    }

}
