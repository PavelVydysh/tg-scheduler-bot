package org.example.infrastructure.dao;

import org.example.infrastructure.entity.AnswerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AnswerDao extends CrudRepository<AnswerEntity, UUID> {
}
