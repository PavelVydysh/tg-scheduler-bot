package org.example.domain.model.poll;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Poll {

    private UUID id;

    private Long tgChatId;

    private String title;

    private Integer version;

    private OffsetDateTime creationDate;

    private List<AvailableAnswer> availableAnswers = new ArrayList<>();

}
