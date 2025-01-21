package com.example.why.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebSocketHandlerConfig {

    @Bean
    public WebSocketHandler myWebSocketHandler() {
        return new WebSocketHandler();
    }
}
