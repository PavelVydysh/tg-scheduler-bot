package org.example.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class State {

    private Long userId;

    private Long chatId;

    private String command;

    private String stateValue;

}
