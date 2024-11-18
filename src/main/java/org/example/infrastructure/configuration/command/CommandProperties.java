package org.example.infrastructure.configuration.command;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommandProperties {

    private String beanName;

    private String pattern;

    private List<String> chatTypes;

}
