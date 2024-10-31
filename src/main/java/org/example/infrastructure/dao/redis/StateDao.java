package org.example.infrastructure.dao.redis;

import org.example.infrastructure.entity.redis.StateHash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateDao extends CrudRepository<StateHash, String> {
}
