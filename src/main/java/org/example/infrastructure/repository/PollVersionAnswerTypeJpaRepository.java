package org.example.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.example.domain.repository.VersionRepository;
import org.example.infrastructure.dao.PollVersionAnswerTypeDao;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PollVersionAnswerTypeJpaRepository implements VersionRepository {

    private final PollVersionAnswerTypeDao pollVersionAnswerTypeDao;

    @Override
    public Integer findLastVersion() {
        return pollVersionAnswerTypeDao.findMaxVersion();
    }
}
