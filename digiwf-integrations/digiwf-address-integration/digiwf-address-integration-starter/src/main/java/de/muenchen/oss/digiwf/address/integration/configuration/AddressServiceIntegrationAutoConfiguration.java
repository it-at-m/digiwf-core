package de.muenchen.oss.digiwf.address.integration.configuration;

import de.muenchen.oss.digiwf.address.integration.gen.ApiClient;
import de.muenchen.oss.digiwf.address.integration.properties.AddressServiceIntegrationProperties;
import de.muenchen.oss.digiwf.address.integration.gen.api.AdressenBundesweitApi;
import de.muenchen.oss.digiwf.address.integration.gen.api.AdressenMnchenApi;
import de.muenchen.oss.digiwf.address.integration.gen.api.StraenMnchenApi;
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
        basePackages = "de.muenchen.oss.digiwf.address.service.integration",
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = {
                                /**
                                 * Exclude to avoid multiple instantiation of beans with same name.
                                 * This class is instantiated in {@link AddressServiceIntegrationAutoConfiguration}
                                 * to give the bean another name.
                                 */
                                ApiClient.class,
                                AdressenBundesweitApi.class,
                                AdressenMnchenApi.class,
                                StraenMnchenApi.class
                        }
                )
        }
)
@EnableConfigurationProperties(AddressServiceIntegrationProperties.class)
public class AddressServiceIntegrationAutoConfiguration {

    public final AddressServiceIntegrationProperties addressServiceIntegrationProperties;

    /**
     * Provides a correct configured {@link ApiClient}.
     *
     * @param restTemplateBuilder to create a {@link RestTemplate}.
     * @return a configured {@link ApiClient}.
     */
    public ApiClient addressServiceApiClient(final RestTemplateBuilder restTemplateBuilder) {

        final RestTemplate restTemplate = restTemplateBuilder
                .build();

        final ApiClient apiClient = new ApiClient(restTemplate);
        apiClient.setBasePath(this.addressServiceIntegrationProperties.getUrl());
        return apiClient;
    }

    /**
     * Create the bean manually to use the correct configured {@link ApiClient}.
     *
     * @param restTemplateBuilder to create a {@link RestTemplate}.
     * @return a bean of type {@link AdressenBundesweitApi} named by method name.
     */
    @Bean
    public AdressenBundesweitApi addressServiceAdressenBundesweitApi(final RestTemplateBuilder restTemplateBuilder) {
        final ApiClient apiClient = this.addressServiceApiClient(restTemplateBuilder);
        return new AdressenBundesweitApi(apiClient);
    }

    /**
     * Create the bean manually to use the correct configured {@link ApiClient}.
     *
     * @param restTemplateBuilder to create a {@link RestTemplate}.
     * @return a bean of type {@link AdressenMnchenApi} named by method name.
     */
    @Bean
    public AdressenMnchenApi addressServiceAdressenMnchenApi(final RestTemplateBuilder restTemplateBuilder) {
        final ApiClient apiClient = this.addressServiceApiClient(restTemplateBuilder);
        return new AdressenMnchenApi(apiClient);
    }

    /**
     * Create the bean manually to use the correct configured {@link ApiClient}.
     *
     * @param restTemplateBuilder to create a {@link RestTemplate}.
     * @return a bean of type {@link StraenMnchenApi} named by method name.
     */
    @Bean
    public StraenMnchenApi addressServiceStraenMnchenApi(final RestTemplateBuilder restTemplateBuilder) {
        final ApiClient apiClient = this.addressServiceApiClient(restTemplateBuilder);
        return new StraenMnchenApi(apiClient);
    }

}
