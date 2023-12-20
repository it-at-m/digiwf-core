package de.muenchen.oss.digiwf.okewo.integration.configuration;

import de.muenchen.oss.digiwf.okewo.integration.client.ApiClient;
import de.muenchen.oss.digiwf.okewo.integration.client.api.PersonApi;
import de.muenchen.oss.digiwf.okewo.integration.client.api.PersonErweitertApi;
import de.muenchen.oss.digiwf.okewo.integration.properties.OkEwoIntegrationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ComponentScan(
    basePackages = "de.muenchen.oss.digiwf.okewo.integration"
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
    try {
      final String newBasePath = updateHostOfBasePath(apiClient.getBasePath(), new URL(okEwoIntegrationProperties.getUrl()));
      apiClient.setBasePath(newBasePath);
    } catch (MalformedURLException exception) {
      log.error("could not update base path of ApiClient because given url is malformed", exception);
    }
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

  /**
   * solution copied from https://github.com/swagger-api/swagger-codegen/issues/2916#issuecomment-220466457
   * @param apiClientBasePath generated api base path of ApiClient
   * @param hostUrl new host or domain of the system
   * @return api base path with given host and base path of ApiClient
   * @throws MalformedURLException
   */
  private String updateHostOfBasePath(String apiClientBasePath, URL hostUrl) throws MalformedURLException {
    URL url = new URL(apiClientBasePath);
    return new URL(hostUrl.getProtocol(), hostUrl.getHost(), hostUrl.getPort(), url.getFile()).toString();
  }
}
