package org.example.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.example.domain.model.AnswerType;
import org.example.domain.repository.AnswerTypeRepository;
import org.example.infrastructure.converter.AnswerTypeEntityConverter;
import org.example.infrastructure.dao.AnswerTypeDao;
import org.example.infrastructure.entity.AnswerTypeEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AnswerTypeJpaRepository implements AnswerTypeRepository {

    private final AnswerTypeDao answerTypeDao;

    @Override
    public List<AnswerType> findAllByVersion(Integer version) {
        List<AnswerTypeEntity> answerTypeEntities = answerTypeDao.findAllByVersion(version);
        List<AnswerType> answerTypes = new ArrayList<>();
        if (!answerTypeEntities.isEmpty()) {
            answerTypeEntities.forEach(answerTypeEntity -> answerTypes.add(AnswerTypeEntityConverter.toAnswerType(answerTypeEntity)));
        }

        return answerTypes;
    }

}
