package com.payten.credit.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfig {
    //  Swagger : http://localhost:8080/swagger-ui/index.html
    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Bootcamp Credit API")
                        .description("Bootcamp Credit API Project")
                        .version("v0.0.1")
                        .contact(new Contact().name("Oguzhan Duymaz").url("github.com/0uz").email("o.duymaz146@gmail.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}