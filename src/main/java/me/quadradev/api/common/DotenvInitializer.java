package me.quadradev.api.common;

import io.github.cdimascio.dotenv.Dotenv;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.ConfigurableEnvironment;

public class DotenvInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        Dotenv dotenv = Dotenv.configure().directory("./").ignoreIfMissing().load();
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        MutablePropertySources propertySources = environment.getPropertySources();
        Map<String, Object> dotenvMap = new HashMap<>();
        dotenv.entries().forEach(entry -> {
            if (!environment.containsProperty(entry.getKey())) {
                dotenvMap.put(entry.getKey(), entry.getValue());
            }
        });
        propertySources.addLast(new MapPropertySource("dotenv", dotenvMap));
    }
}
