package com.epam.potato.rest.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Value("${swagger.enabled}")
    private boolean swaggerEnabled;
    @Value("${swagger.api.info.title}")
    private String title;
    @Value("${swagger.api.info.description}")
    private String description;
    @Value("${swagger.api.info.version}")
    private String version;
    @Value("${swagger.api.info.contact.name}")
    private String contactName;
    @Value("${swagger.api.info.contact.email}")
    private String contactEmail;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
            .paths(PathSelectors.any())
            .build();//.apiInfo(apiInfo()).enable(swaggerEnabled);
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(title, description, version, null, contact(), null, null, Collections.EMPTY_LIST);
    }

    private Contact contact() {
        return new Contact(contactName, null, contactEmail);
    }

}
