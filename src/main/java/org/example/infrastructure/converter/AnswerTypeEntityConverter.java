package org.example.infrastructure.converter;

import org.example.domain.model.AnswerType;
import org.example.infrastructure.entity.AnswerTypeEntity;

import java.util.ArrayList;
import java.util.List;

public class AnswerTypeEntityConverter {

    public static AnswerType toAnswerType(AnswerTypeEntity answerTypeEntity) {
        AnswerType answerType = new AnswerType();

        answerType.setId(answerTypeEntity.getId());
        answerType.setTitle(answerTypeEntity.getTitle());
        answerType.setPosition(answerTypeEntity.getPosition());

        List<Integer> versions = new ArrayList<>();
        answerTypeEntity.getVersions().forEach(version -> versions.add(version.getVersion()));
        answerType.setVersions(versions);

        return answerType;
    }

}
