package org.example.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSession {

    private Long userId;

    private String command;

}
