package org.example.api.converter.poll;

import org.example.api.dto.poll.PollCreateRequestDto;
import org.example.domain.model.poll.Poll;
import org.springframework.util.ObjectUtils;
import org.telegram.telegrambots.meta.api.objects.message.Message;

public class PollConverter {

    public static Poll toPoll(PollCreateRequestDto pollDto) {
        if (ObjectUtils.isEmpty(pollDto)) {
            return null;
        }

        Poll poll = new Poll();
        poll.setTitle(pollDto.getTitle());
        poll.setAvailableAnswers(
                AvailableAnswerConverter.toListAvailableAnswer(pollDto.getAvailableAnswers())
        );

        return poll;
    }

}
