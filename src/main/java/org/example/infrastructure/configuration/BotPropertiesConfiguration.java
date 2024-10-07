package org.example.infrastructure.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotPropertiesConfiguration {

    @Bean
    @ConfigurationProperties(prefix = Environment.BOT_CONFIGURATION_PREFIX)
    public BotProperties botProperties() {
        return new BotProperties();
    }

}
