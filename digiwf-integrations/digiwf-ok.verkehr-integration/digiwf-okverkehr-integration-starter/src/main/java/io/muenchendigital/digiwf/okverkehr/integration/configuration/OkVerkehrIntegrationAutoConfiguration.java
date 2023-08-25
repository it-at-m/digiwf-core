package io.muenchendigital.digiwf.okverkehr.integration.configuration;

import io.muenchendigital.digiwf.okverkehr.integration.gen.ApiClient;
import io.muenchendigital.digiwf.okverkehr.integration.gen.api.BusinessActionskfzhalterpersonenApi;
import io.muenchendigital.digiwf.okverkehr.integration.properties.OkVerkehrIntegrationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
@ComponentScan(
        basePackages = "io.muenchendigital.digiwf.okverkehr.integration",
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = {
                                /**
                                 * Exclude to avoid multiple instantiation of beans with same name.
                                 * This class is instantiated in {@link OkVerkehrIntegrationAutoConfiguration}
                                 * to give the bean another name.
                                 */
                                ApiClient.class,
                                BusinessActionskfzhalterpersonenApi.class
                        }
                )
        }
)
@EnableConfigurationProperties(OkVerkehrIntegrationProperties.class)
public class OkVerkehrIntegrationAutoConfiguration {

    public final OkVerkehrIntegrationProperties okVerkehrIntegrationProperties;

    /**
     * Provides a correct configured {@link ApiClient}.
     *
     * @param restTemplateBuilder to create a {@link RestTemplate}.
     * @return a configured {@link ApiClient}.
     */
    public ApiClient okVerkehrApiClient(final RestTemplateBuilder restTemplateBuilder) {
        final RestTemplate restTemplate = restTemplateBuilder
                .basicAuthentication(
                        this.okVerkehrIntegrationProperties.getUsername(),
                        this.okVerkehrIntegrationProperties.getPassword()
                )
                .build();
        final ApiClient apiClient = new ApiClient(restTemplate);
        apiClient.setBasePath(this.okVerkehrIntegrationProperties.getUrl());
        return apiClient;
    }

    /**
     * Create the bean manually to use the correct configured {@link ApiClient}.
     *
     * @param restTemplateBuilder to create a {@link RestTemplate}.
     * @return a bean of type {@link BusinessActionskfzhalterpersonenApi} named by method name.
     */
    @Bean
    public BusinessActionskfzhalterpersonenApi okVerkehrBusinessActionskfzhalterpersonenApi(final RestTemplateBuilder restTemplateBuilder) {
        final ApiClient apiClient = this.okVerkehrApiClient(restTemplateBuilder);
        return new BusinessActionskfzhalterpersonenApi(apiClient);
    }

}
