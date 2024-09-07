package com.example.spring_bot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource("application.properties")
public class BotConfig {
    @Value("${bot.name}")
    private String bot_name;
    @Value("${bot.token}")
    private String bot_token;
}
