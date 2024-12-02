package org.example.domain.model.poll;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Poll {

    private UUID pollId;

    private String title;

    private List<AvailableAnswer> availableAnswers;

}
