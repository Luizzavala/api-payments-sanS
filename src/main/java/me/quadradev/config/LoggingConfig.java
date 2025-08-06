package me.quadradev.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoggingConfig {

    @PostConstruct
    public void init() {
        log.info("Logging configured");
    }
}
