package io.muenchendigital.digiwf.okewo.integration.configuration;

import io.muenchendigital.digiwf.okewo.integration.gen.ApiClient;
import io.muenchendigital.digiwf.okewo.integration.gen.api.PersonApi;
import io.muenchendigital.digiwf.okewo.integration.gen.api.PersonErweitertApi;
import io.muenchendigital.digiwf.okewo.integration.properties.OkEwoIntegrationProperties;
import io.muenchendigital.digiwf.okewo.integration.service.PropertiesService;
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
        basePackages = "io.muenchendigital.digiwf.okewo.integration",
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = {
                                /**
                                 * Exclude to avoid multiple instantiation of beans with same name.
                                 * This class is instantiated in {@link OkEwoIntegrationAutoConfiguration}
                                 * to give the bean another name.
                                 */
                                ApiClient.class,
                                PersonApi.class,
                                PersonErweitertApi.class
                        }
                )
        }
)
@EnableConfigurationProperties(OkEwoIntegrationProperties.class)
public class OkEwoIntegrationAutoConfiguration {

    public final OkEwoIntegrationProperties okEwoIntegrationProperties;

    /**
     * Provides a correct configured {@link ApiClient}.
     *
     * @param restTemplateBuilder to create a {@link RestTemplate}.
     * @return a configured {@link ApiClient}.
     */
    public ApiClient okEwoApiClient(final RestTemplateBuilder restTemplateBuilder) {
        final RestTemplate restTemplate = restTemplateBuilder
                .basicAuthentication(
                        this.okEwoIntegrationProperties.getUsername(),
                        this.okEwoIntegrationProperties.getPassword()
                )
                .build();
        final ApiClient apiClient = new ApiClient(restTemplate);
        apiClient.setBasePath(this.okEwoIntegrationProperties.getUrl());
        return apiClient;
    }

    /**
     * Create the bean manually to use the correct configured {@link ApiClient}.
     *
     * @param restTemplateBuilder to create a {@link RestTemplate}.
     * @return a bean of type {@link PersonApi} named by method name.
     */
    @Bean
    public PersonApi okEwoPersonApi(final RestTemplateBuilder restTemplateBuilder) {
        final ApiClient apiClient = this.okEwoApiClient(restTemplateBuilder);
        return new PersonApi(apiClient);
    }

    /**
     * Create the bean manually to use the correct configured {@link ApiClient}.
     *
     * @param restTemplateBuilder to create a {@link RestTemplate}.
     * @return a bean of type {@link PersonErweitertApi} named by method name.
     */
    @Bean
    public PersonErweitertApi okEwoPersonErweitertApi(final RestTemplateBuilder restTemplateBuilder) {
        final ApiClient apiClient = this.okEwoApiClient(restTemplateBuilder);
        return new PersonErweitertApi(apiClient);
    }

    /**
     * @return a bean of type {@link PropertiesService} named by method name.
     */
    @Bean
    public PropertiesService propertiesService() {
        return new PropertiesService(this.okEwoIntegrationProperties.getBenutzerId());
    }

}
