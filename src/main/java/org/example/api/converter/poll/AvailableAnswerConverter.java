package org.example.api.converter.poll;

import org.example.api.dto.poll.AvailableAnswerCreateRequestDto;
import org.example.domain.model.poll.AvailableAnswer;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;

public class AvailableAnswerConverter {

    public static AvailableAnswer toAvailableAnswer(AvailableAnswerCreateRequestDto availableAnswerDto) {
        if (ObjectUtils.isEmpty(availableAnswerDto)) {
            return null;
        }

        AvailableAnswer availableAnswer = new AvailableAnswer();
        availableAnswer.setTitle(availableAnswerDto.getTitle());
        availableAnswer.setPosition(availableAnswerDto.getPosition());

        return availableAnswer;
    }

    public static List<AvailableAnswer> toListAvailableAnswer(List<AvailableAnswerCreateRequestDto> availableAnswerDtos) {
        if (CollectionUtils.isEmpty(availableAnswerDtos)) {
            return Collections.emptyList();
        }

        return availableAnswerDtos.stream()
                .map(AvailableAnswerConverter::toAvailableAnswer)
                .toList();
    }

}
