package com.pauloeduardocosta.auth.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@Profile("dev")
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.pauloeduardocosta.auth.v1.rs"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("Auth-API",
                "MS de autenticação",
                "1.0.0",
                "",
                apiContact(),
                "Licença da API",
                "https://github.com/Eduardo32/auth/blob/master/LICENSE",
                Collections.emptyList());
    }

    private Contact apiContact() {
        return new Contact(
                "Paulo Eduardo Costa",
                "https://www.github.com/Eduardo32",
                "contato@pauloeduardocosta.com"
        );
    }
}
