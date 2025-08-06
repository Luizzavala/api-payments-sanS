package me.quadradev.api;

import me.quadradev.api.common.DotenvInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.Banner;

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ApiApplication.class);
        application.addInitializers(new DotenvInitializer());
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

}
