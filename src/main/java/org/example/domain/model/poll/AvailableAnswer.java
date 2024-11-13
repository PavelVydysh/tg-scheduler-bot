package org.example.domain.model.poll;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class AvailableAnswer {

    private String title;

    private Integer position;

}
