package org.example.infrastructure.configuration.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StateEntity {

    private Long userId;

    private Long chatId;

    private String command;

    private String stateValue;

}
