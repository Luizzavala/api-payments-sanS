package me.quadradev.api;

import me.quadradev.api.infrastructure.config.DotenvInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.Banner;

@SpringBootApplication(
        scanBasePackages = {
                "me.quadradev.api.domain",
                "me.quadradev.api.application",
                "me.quadradev.api.infrastructure",
                "me.quadradev.api.adapters"
        }
)
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ApiApplication.class);
        application.addInitializers(new DotenvInitializer());
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

}
