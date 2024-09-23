package org.example.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomPoll {

    private String id;

    private Long chatId;

    private String title;

    private LocalDate createdDate;

    private Integer version;

    private List<Answer> usersAnswers;

    private List<AnswerType> availableAnswerTypes;
}
