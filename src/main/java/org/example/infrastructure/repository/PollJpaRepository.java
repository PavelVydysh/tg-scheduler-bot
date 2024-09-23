package org.example.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.example.domain.model.CustomPoll;
import org.example.domain.repository.PollRepository;
import org.example.infrastructure.converter.PollEntityConverter;
import org.example.infrastructure.dao.PollDao;
import org.example.infrastructure.entity.PollEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PollJpaRepository implements PollRepository {

    private final PollDao pollDao;

    @Override
    public CustomPoll create(CustomPoll customPoll) {
        PollEntity pollEntity = PollEntityConverter.toPollEntity(customPoll);
        pollEntity = pollDao.save(pollEntity);
        return PollEntityConverter.toCustomPollSquash(pollEntity);
    }

}
