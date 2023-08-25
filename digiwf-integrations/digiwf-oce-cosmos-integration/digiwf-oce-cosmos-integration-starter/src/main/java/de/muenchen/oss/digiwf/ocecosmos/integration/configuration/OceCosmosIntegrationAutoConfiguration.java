package de.muenchen.oss.digiwf.ocecosmos.integration.configuration;

import de.muenchen.oss.digiwf.ocecosmos.integration.gen.ApiClient;
import de.muenchen.oss.digiwf.ocecosmos.integration.properties.OceCosmosIntegrationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;


@Configuration
@RequiredArgsConstructor
@ComponentScan(
        basePackages = "de.muenchen.oss.digiwf.ocecosmos.integration",
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = {
                                /**
                                 * Exclude to make custom configurations.
                                 * This class is instantiated in {@link OceCosmosIntegrationAutoConfiguration}.
                                 */
                                ApiClient.class
                        }
                )
        }
)
@EnableConfigurationProperties(OceCosmosIntegrationProperties.class)
public class OceCosmosIntegrationAutoConfiguration {

    public final OceCosmosIntegrationProperties oceCosmosIntegrationProperties;

    /**
     * Provides a correct configured {@link ApiClient}.
     *
     * @param restTemplate to access resource.
     * @return a configured {@link ApiClient}.
     */
    @Bean
    public ApiClient oceCosmosApiClient(final RestTemplate restTemplate) {

        final ApiClient apiClient = new ApiClient(restTemplate);
        apiClient.setBasePath(this.oceCosmosIntegrationProperties.getUrl());
        return apiClient;
    }



}
