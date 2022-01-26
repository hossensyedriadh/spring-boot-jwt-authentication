package io.github.hossensyedriadh.springbootjwtauthentication.configuration.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@EnableSwagger2
@EnableOpenApi
@Import({BeanValidatorPluginsConfiguration.class})
public class SwaggerConfiguration {
    @Value("${info.application.metadata.name}")
    private String applicationName;

    @Value("${info.application.metadata.description}")
    private String applicationDescription;

    @Value("${info.application.metadata.version}")
    private String applicationVersion;

    @Value("${info.application.developer.name}")
    private String developerName;

    @Value("${info.application.developer.url}")
    private String developerUrl;

    @Value("${info.application.developer.email}")
    private String developerEmail;

    @Value("${server.servlet.context-path}")
    private String applicationContextPath;

    @Bean
    public Docket swaggerUiConfiguration() {
        Set<String> protocols = new HashSet<>();
        protocols.add("http");
        protocols.add("https");

        Set<String> producedTypes = new HashSet<>();
        producedTypes.add(MediaType.APPLICATION_JSON_VALUE);

        return new Docket(DocumentationType.OAS_30).securityContexts(List.of(this.securityContext()))
                .securitySchemes(List.of(this.apiKey()))
                .select().apis(RequestHandlerSelectors.basePackage("io.github.hossensyedriadh.springbootjwtauthentication.controller.v1.authentication")
                        .or(RequestHandlerSelectors.basePackage("io.github.hossensyedriadh.springbootjwtauthentication.controller.v1.resource")))
                .paths(PathSelectors.ant("/" + applicationContextPath + "/**")).build()
                .apiInfo(this.metadata()).protocols(protocols).produces(producedTypes);
    }

    private ApiInfo metadata() {
        return new ApiInfo(applicationName, applicationDescription, applicationVersion,
                null, new Contact(developerName, developerUrl, developerEmail), "Apache License 2.0",
                "https://github.com/hossensyedriadh/spring-boot-jwt-authentication/blob/main/LICENSE", Collections.emptyList());
    }

    private ApiKey apiKey() {
        return new ApiKey(HttpHeaders.AUTHORIZATION, HttpHeaders.AUTHORIZATION, "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(this.defaultAuthentication()).build();
    }

    private List<SecurityReference> defaultAuthentication() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "Access secured endpoints");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;

        return List.of(new SecurityReference(HttpHeaders.AUTHORIZATION, authorizationScopes));
    }
}
