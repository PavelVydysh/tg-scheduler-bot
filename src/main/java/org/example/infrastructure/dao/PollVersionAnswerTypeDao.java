package org.example.infrastructure.dao;

import org.example.infrastructure.entity.PollVersionAnswerTypeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PollVersionAnswerTypeDao extends CrudRepository<PollVersionAnswerTypeEntity, UUID> {

    @Query("""
            SELECT MAX(pv.version) FROM PollVersionAnswerTypeEntity pv
            """)
    Integer findMaxVersion();

}
