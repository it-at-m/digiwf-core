package io.muenchendigital.digiwf.s3.integration.client.configuration;

import io.muenchendigital.digiwf.s3.integration.client.properties.S3IntegrationClientProperties;
import io.muenchendigital.digiwf.s3.integration.client.service.ApiClientFactory;
import io.muenchendigital.digiwf.s3.integration.gen.ApiClient;
import io.muenchendigital.digiwf.s3.integration.gen.api.FileApiApi;
import io.muenchendigital.digiwf.s3.integration.gen.api.FolderApiApi;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.reactive.function.client.WebClient;

@ComponentScan(
        basePackages = {
                "io.muenchendigital.digiwf.s3.integration.gen",
                "io.muenchendigital.digiwf.s3.integration.client"
        },
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = {
                                /**
                                 * Exclude to avoid multiple instantiation of multiple beans with same name.
                                 * This class is instantiated in {@link S3IntegrationClientAutoConfiguration}
                                 * to give the bean another name.
                                 */
                                ApiClient.class,
                                FileApiApi.class,
                                FolderApiApi.class
                        }
                )
        })
@RequiredArgsConstructor
@EnableConfigurationProperties(S3IntegrationClientProperties.class)
public class S3IntegrationClientAutoConfiguration {

    public final S3IntegrationClientProperties s3IntegrationClientProperties;

    /**
     * Creates a bean with name "apiClientFactory" of {@link ApiClientFactory}.
     * <p>
     * This factory class is providing either the preconfigured {@link FileApiApi} or {@link FileApiApi}.
     *
     * @param webClient to create rest requests.
     *                  If the S3 integration service is secured via Oauth2,
     * @return the {@link ApiClientFactory}.
     */
    @Bean
    public ApiClientFactory apiClientFactory(final WebClient webClient) {
        return new ApiClientFactory(
                this.s3IntegrationClientProperties.getDocumentStorageUrl(),
                webClient
        );
    }

}
