package org.example.infrastructure.converter;

import org.example.domain.model.poll.AvailableAnswer;
import org.example.infrastructure.feign.dto.AvailableAnswerRequestClientDto;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;

public class AvailableAnswerClientConverter {

    public static AvailableAnswerRequestClientDto toAvailableAnswerClientRequestDto(AvailableAnswer availableAnswer) {
        if (ObjectUtils.isEmpty(availableAnswer)) {
            return null;
        }

        AvailableAnswerRequestClientDto availableAnswerDto = new AvailableAnswerRequestClientDto();
        availableAnswerDto.setTitle(availableAnswer.getTitle());
        availableAnswerDto.setPosition(availableAnswer.getPosition());

        return availableAnswerDto;
    }

    public static List<AvailableAnswerRequestClientDto> toListAvailableAnswerRequestClientDto(List<AvailableAnswer> availableAnswers) {
        if (CollectionUtils.isEmpty(availableAnswers)) {
            return Collections.emptyList();
        }

        return availableAnswers.stream()
                .map(AvailableAnswerClientConverter::toAvailableAnswerClientRequestDto)
                .toList();
    }

}
