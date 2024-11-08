package org.example.infrastructure.converter;

import org.example.domain.model.poll.AvailableAnswer;
import org.example.infrastructure.entity.AvailableAnswerHash;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;

public class AvailableAnswerHashConverter {

    public static AvailableAnswerHash toAvailableAnswerHash(AvailableAnswer availableAnswer) {
        if (ObjectUtils.isEmpty(availableAnswer)) {
            return null;
        }

        AvailableAnswerHash availableAnswerHash = new AvailableAnswerHash();
        availableAnswerHash.setTitle(availableAnswer.getTitle());
        availableAnswerHash.setPosition(availableAnswer.getPosition());

        return availableAnswerHash;
    }

    public static List<AvailableAnswerHash> toListAvailableAnswerHash(List<AvailableAnswer> availableAnswers) {
        if (CollectionUtils.isEmpty(availableAnswers)) {
            return Collections.emptyList();
        }

        return availableAnswers.stream()
                .map(AvailableAnswerHashConverter::toAvailableAnswerHash)
                .toList();
    }

    public static AvailableAnswer toAvailableAnswer(AvailableAnswerHash availableAnswerHash) {
        if (ObjectUtils.isEmpty(availableAnswerHash)) {
            return null;
        }

        AvailableAnswer availableAnswer = new AvailableAnswer();
        availableAnswer.setTitle(availableAnswer.getTitle());
        availableAnswer.setPosition(availableAnswerHash.getPosition());

        return availableAnswer;
    }

    public static List<AvailableAnswer> toListAvailableAnswers(List<AvailableAnswerHash> availableAnswersHashes) {
        if (CollectionUtils.isEmpty(availableAnswersHashes)) {
            return Collections.emptyList();
        }

        return availableAnswersHashes.stream()
                .map(AvailableAnswerHashConverter::toAvailableAnswer)
                .toList();
    }

}
