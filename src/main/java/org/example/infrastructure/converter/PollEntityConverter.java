package org.example.infrastructure.converter;

import org.example.domain.model.CustomPoll;
import org.example.infrastructure.entity.PollEntity;

public class PollEntityConverter {

    public static PollEntity toPollEntity(CustomPoll customPoll) {
        PollEntity pollEntity = new PollEntity();
        pollEntity.setId(customPoll.getId());
        pollEntity.setChatId(customPoll.getChatId().toString());
        pollEntity.setVersion(customPoll.getVersion());
        pollEntity.setTitle(customPoll.getTitle());
        pollEntity.setCreatedDate(customPoll.getCreatedDate());

        return pollEntity;
    }

    public static CustomPoll toCustomPollSquash(PollEntity pollEntity) {
        CustomPoll customPoll = new CustomPoll();
        customPoll.setId(pollEntity.getId());
        customPoll.setTitle(pollEntity.getTitle());
        customPoll.setVersion(pollEntity.getVersion());
        customPoll.setCreatedDate(pollEntity.getCreatedDate());

        return customPoll;
    }

}
