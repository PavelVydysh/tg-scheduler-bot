package org.example.infrastructure.dao;

import org.example.infrastructure.entity.PollEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollDao extends CrudRepository<PollEntity, Long> {
}
