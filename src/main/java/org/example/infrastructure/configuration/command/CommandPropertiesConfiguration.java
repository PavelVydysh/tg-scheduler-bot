package org.example.infrastructure.configuration.command;

import org.example.api.command.Command;
import org.example.infrastructure.configuration.Environment;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CommandPropertiesConfiguration {

    @Bean(name = "commands")
    public Map<String, Command> commands(
            Map<String, Command> commands,
            Map<String, CommandProperties> commandsProperties
    ) {
        Map<String, Command> configuredCommands = new HashMap<>();

        for (CommandProperties cp : commandsProperties.values()) {
            Command currentCommand = commands.get(cp.getBeanName());
            currentCommand.setPattern(cp.getPattern());
            currentCommand.setAllowedChatTypes(cp.getChatTypes());
            configuredCommands.put(currentCommand.getPattern(), currentCommand);
        }

        return configuredCommands;
    }

    @Bean
    @ConfigurationProperties(prefix = Environment.COMMANDS_CONFIGURATION_PREFIX)
    public Map<String, CommandProperties> commandsProperties() {
        return new HashMap<>();
    }

}
