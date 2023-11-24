package de.muenchen.oss.digiwf.okewo.integration.configuration;

import de.muenchen.oss.digiwf.okewo.integration.client.ApiClient;
import de.muenchen.oss.digiwf.okewo.integration.client.api.PersonApi;
import de.muenchen.oss.digiwf.okewo.integration.client.api.PersonErweitertApi;
import de.muenchen.oss.digiwf.okewo.integration.properties.OkEwoIntegrationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
@RequiredArgsConstructor
@ComponentScan(
    basePackages = "de.muenchen.oss.digiwf.okewo.integration",
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
   * @return a configured {@link ApiClient}.
   */
  public ApiClient okEwoApiClient() {
    final WebClient webClient = WebClient.builder()
        .baseUrl(okEwoIntegrationProperties.getUrl())
        .filter(ExchangeFilterFunctions
            .basicAuthentication(okEwoIntegrationProperties.getUsername(), okEwoIntegrationProperties.getPassword()))
        .build();
    final ApiClient apiClient = new ApiClient(webClient);
    apiClient.setBasePath(this.okEwoIntegrationProperties.getUrl());
    return apiClient;
  }

  /**
   * Create the bean manually to use the correct configured {@link ApiClient}.
   *
   * @return a bean of type {@link PersonApi} named by method name.
   */
  @Bean
  public PersonApi okEwoPersonApi() {
    final ApiClient apiClient = this.okEwoApiClient();
    return new PersonApi(apiClient);
  }

  /**
   * Create the bean manually to use the correct configured {@link ApiClient}.
   *
   * @return a bean of type {@link PersonErweitertApi} named by method name.
   */
  @Bean
  public PersonErweitertApi okEwoPersonErweitertApi() {
    final ApiClient apiClient = this.okEwoApiClient();
    return new PersonErweitertApi(apiClient);
  }

//    /**
//     * @return a bean of type {@link PropertiesServiceTemplate} named by method name.
//     */
//    @Bean
//    public PropertiesServiceTemplate propertiesService() {
//        return new PropertiesServiceTemplate(this.okEwoIntegrationProperties.getBenutzerId());
//    }

}
