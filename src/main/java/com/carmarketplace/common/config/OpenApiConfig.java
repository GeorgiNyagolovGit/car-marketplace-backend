package com.carmarketplace.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    public static final String SESSION_COOKIE_SECURITY_SCHEME = "sessionCookie";

    @Bean
    OpenAPI carMarketplaceOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Car Marketplace API")
                        .description("Use POST /api/auth/login first. Swagger UI keeps the JSESSIONID cookie "
                                + "and sends it with the protected requests.")
                        .version("1.0.0"))
                .components(new Components().addSecuritySchemes(
                        SESSION_COOKIE_SECURITY_SCHEME,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.COOKIE)
                                .name("JSESSIONID")
                                .description("Session cookie returned by POST /api/auth/login")));
    }
}
