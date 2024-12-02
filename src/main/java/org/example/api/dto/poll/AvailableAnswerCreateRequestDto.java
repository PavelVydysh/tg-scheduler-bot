package org.example.api.dto.poll;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvailableAnswerCreateRequestDto {

    private String title;

    private Integer position;

}
