package me.quadradev.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "me.quadradev.application.core.repository")
@EntityScan(basePackages = "me.quadradev.application.core.model")
@EnableJpaAuditing
public class DatabaseConfig {
}
