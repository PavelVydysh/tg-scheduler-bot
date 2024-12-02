package org.example.infrastructure.feign.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AvailableAnswerRequestClientDto {

    private String title;

    private Integer position;

}
