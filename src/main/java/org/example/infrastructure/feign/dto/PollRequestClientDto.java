package org.example.infrastructure.feign.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PollRequestClientDto {

    private String title;

    private List<AvailableAnswerRequestClientDto> availableAnswers;

}
