package org.example.domain.model.poll;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Poll {

    private String pollId;

    private String title;

    private Long tgChatId;

    private Long tgUserId;

    private Boolean isAnonymous;

    private Boolean isHeaderDateTime;

    private List<AvailableAnswer> availableAnswers;

    public Poll() {
        this.isAnonymous = true;
        this.isHeaderDateTime = false;
    }

    public void addAvailableAnswer(AvailableAnswer availableAnswer) {
        List<AvailableAnswer> availableAnswersToEnrich = new ArrayList<>(availableAnswers);
        availableAnswersToEnrich.add(availableAnswer);
        availableAnswers = availableAnswersToEnrich;
    }

}
