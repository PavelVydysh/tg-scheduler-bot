package org.example.api.mapper;

import org.example.domain.model.CustomPoll;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;

public class PollMapper {

    public static void toCustomPoll(CustomPoll customPoll, Poll poll) {
        customPoll.setId(poll.getId());
    }

}
