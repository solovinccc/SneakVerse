package com.vincenzolisi.SneakVerse.Configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public OpenAPI sneakVerseOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SneakVerse API")
                        .description("API REST per la gestione di utenti, ordini, spedizioni e corrieri")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Vincenzo Lisi")
                                .email("vincenzo@example.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT"))
                );
    }
}
