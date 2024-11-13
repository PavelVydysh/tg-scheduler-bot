package org.example.infrastructure.dao;

import org.example.infrastructure.entity.StateHash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateDao extends CrudRepository<StateHash, String> {
}
