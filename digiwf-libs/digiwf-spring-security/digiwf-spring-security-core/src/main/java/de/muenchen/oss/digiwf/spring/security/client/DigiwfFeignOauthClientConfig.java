package de.muenchen.oss.digiwf.spring.security.client;

import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

/**
 * Configures the OAuth2 request interceptor for Feign clients.
 */
@RequiredArgsConstructor
public class DigiwfFeignOauthClientConfig {

    private final OAuth2AccessTokenSupplier oAuth2AccessTokenSupplier;

    @Bean
    public RequestInterceptor oAuth2RequestInterceptor() {
        return (requestTemplate ->
                requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer " + oAuth2AccessTokenSupplier.get().getTokenValue()));
    }
}
