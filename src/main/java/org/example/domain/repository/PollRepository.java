package org.example.domain.repository;

import org.example.domain.model.CustomPoll;

public interface PollRepository {

    CustomPoll create(CustomPoll customPoll);

}
