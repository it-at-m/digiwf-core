package io.muenchendigital.digiwf.s3.integration.client.configuration;

import io.muenchendigital.digiwf.s3.integration.gen.ApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

    /**
     * Provides the {@link RestTemplate} which is used in {@link ApiClient}.
     *
     * @return the {@link RestTemplate}.
     */
    @Bean
    public RestTemplate restTemplate() {
        /**
         * Add {@link HttpComponentsClientHttpRequestFactory} to rest template to allow
         * {@link org.springframework.http.HttpMethod.PATCH} requests.
         */
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }

}
