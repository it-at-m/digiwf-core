/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.shared.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    private final String authServer;
    private final String realm;

    @Value("${info.application.version}")
    private String buildVersion;

    @Autowired
    public SwaggerConfig(
            @Value("${SSO_BASE_URL}") final String authServer,
            @Value("${SSO_REALM}") final String realm) {
        this.authServer = authServer;
        this.realm = realm;
    }

    @Bean
    public OpenAPI openAPI() {
        final String authUrl = String.format("%s/realms/%s/protocol/openid-connect", this.authServer, this.realm);
        return new OpenAPI()
                .components(
                        new Components()
                                .addSecuritySchemes("spring_oauth", new SecurityScheme()
                                        .type(SecurityScheme.Type.OAUTH2)
                                        .description("Oauth2 flow")
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .flows(new OAuthFlows()
                                                .password(new OAuthFlow()
                                                        .authorizationUrl(authUrl + "/auth")
                                                        .refreshUrl(authUrl + "/token")
                                                        .tokenUrl(authUrl + "/token")
                                                        .scopes(new Scopes().addString("lhm_extended", "lhm_extended")))))
                )
                .security(Collections.singletonList(
                        new SecurityRequirement().addList("spring_oauth")))
                .info(new Info()
                        .title("DigiWF API")
                        .version(this.buildVersion)
                        .description("DigiWF - Plattform zur Digitalisierung von Workflows bei der LHM")
                        .contact(new Contact()
                                .name("DigiWF")
                                .email("itm.digiwf@muenchen.de")))
                .externalDocs(new ExternalDocumentation()
                        .description("Externe Dokumentation auf unserer Wilma-Seite")
                        .url("https://wilma.muenchen.de/workspaces/digitale-workflows/apps/wiki/wiki/list"));
    }

    @Bean
    @Profile("!prod")
    public String[] whitelist() {
        return new String[]{
                // -- swagger ui
                "/v2/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/swagger-ui/**",
                "/swagger-ui.html",
        };
    }

    @Bean
    @Profile("prod")
    public String[] whitelistProd() {
        return new String[]{};
    }

}
