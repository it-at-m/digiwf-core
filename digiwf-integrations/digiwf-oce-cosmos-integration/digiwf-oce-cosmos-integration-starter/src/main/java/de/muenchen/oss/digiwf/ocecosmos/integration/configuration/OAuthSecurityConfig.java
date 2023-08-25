package de.muenchen.oss.digiwf.ocecosmos.integration.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
public class OAuthSecurityConfig {

    @Bean
    public OAuth2RestTemplate restTemplate() {
        return new OAuth2RestTemplate(cosmosWebauthClient());
    }

    @Bean
    @ConfigurationProperties("de.muenchen.oss.digiwf.ocecosmos.oauth-client")
    public OAuth2ProtectedResourceDetails cosmosWebauthClient() {
        return new ResourceOwnerPasswordResourceDetails();
    }
}