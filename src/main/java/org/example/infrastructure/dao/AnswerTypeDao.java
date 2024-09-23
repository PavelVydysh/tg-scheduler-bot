package org.example.infrastructure.dao;

import org.example.infrastructure.entity.AnswerTypeEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnswerTypeDao extends CrudRepository<AnswerTypeEntity, UUID> {

    @EntityGraph(attributePaths = {AnswerTypeEntity.VERSIONS_FIELD_NAME})
    @Query("""
            SELECT at FROM AnswerTypeEntity at
            JOIN PollVersionAnswerTypeEntity pvat
            ON at = pvat.answerType
            WHERE pvat.version = :version
            """)
    List<AnswerTypeEntity> findAllByVersion(@Param("version") Integer version);

}
