package de.muenchen.oss.digiwf.connector;

import de.muenchen.oss.digiwf.spring.security.client.OAuth2AccessTokenSupplier;
import feign.RequestInterceptor;
import lombok.AllArgsConstructor;
import org.camunda.bpm.client.interceptor.ClientRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;

@Configuration
@Profile("!no-security")
@AllArgsConstructor
public class CamundaSecurityConfig {

    private final OAuth2AccessTokenSupplier tokenSupplier;

    // Camunda External Task
    @Bean
    public ClientRequestInterceptor interceptor() {
        return context ->
            context.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + this.tokenSupplier.get().getTokenValue());
    }

    // Feign
    @Bean
    public RequestInterceptor oAuth2RequestInterceptor() {
        return (requestTemplate ->
                requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenSupplier.get().getTokenValue()));
    }
}
