package de.muenchen.oss.digiwf.process.api.config.impl;

import de.muenchen.oss.digiwf.spring.security.client.OAuth2AccessTokenSupplier;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
public class FeignConfig {

    private final OAuth2AccessTokenSupplier oAuth2AccessTokenSupplier;

    @Bean
    public RequestInterceptor oAuth2RequestInterceptor() {
        return (requestTemplate ->
                requestTemplate.header("Authorization", "Bearer " + oAuth2AccessTokenSupplier.get().getTokenValue()));
    }
}
