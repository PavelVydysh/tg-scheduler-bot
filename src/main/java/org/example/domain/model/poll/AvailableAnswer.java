package org.example.domain.model.poll;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AvailableAnswer {

    private UUID availableAnswerId;

    private String title;

    private Integer position;

}
