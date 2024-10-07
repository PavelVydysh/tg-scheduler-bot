package org.example.infrastructure.configuration;

import org.example.api.command.Command;
import org.example.api.command.InfoCommand;
import org.example.api.command.StatusCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CommandConfiguration {

    @Bean
    public Map<String, Command> commands(InfoCommand infoCommand,
                                         StatusCommand statusCommand) {
        return Map.of(
                "info", infoCommand,
                "status", statusCommand
        );
    }

}
