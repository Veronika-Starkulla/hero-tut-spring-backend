package de.navimatix.hero_backend;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HeroBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeroBackendApplication.class, args);
    }

    @Bean
    public OpenAPI openApiConfic() {
        return new OpenAPI().info(new Info().title("Hero Tutorial").description("Backend").version("v1.0.0"));
    }
}
