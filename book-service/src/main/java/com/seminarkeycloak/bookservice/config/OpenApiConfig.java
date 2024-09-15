package com.seminarkeycloak.bookservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("Book-Service"))
                .components(new Components()
                        .addSecuritySchemes("Book-Service",
                                new SecurityScheme()
                                        .openIdConnectUrl("http://localhost:8181/realms/Seminar/.well-known/openid-configuration")
                                        .scheme("bearer")
                                        .type(SecurityScheme.Type.OPENIDCONNECT)
                                        .in(SecurityScheme.In.HEADER)
                        )
                );
    }
}
