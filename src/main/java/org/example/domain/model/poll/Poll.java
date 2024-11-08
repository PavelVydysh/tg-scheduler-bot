package org.example.domain.model.poll;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Poll {

    private String pollId;

    private String title;

    private Long tgChatId;

    private Long tgUserId;

    private List<AvailableAnswer> availableAnswers;

}
