package org.example.domain.repository;

import org.example.domain.model.AnswerType;

import java.util.List;

public interface AnswerTypeRepository {

    List<AnswerType> findAllByVersion(Integer version);

}
