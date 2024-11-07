package org.example.domain.model.poll;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Poll {

    private UUID pollId;

    private String title;

    private String tgChatId;

    private OffsetDateTime creationDate;

    private Integer version;

    private List<AvailableAnswer> availableAnswers;

}
