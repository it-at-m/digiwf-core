package de.muenchen.oss.digiwf.alw.integration.adapter.out.alw;

import de.muenchen.oss.digiwf.alw.integration.application.port.out.AlwResponsibilityOutPort;
import de.muenchen.oss.digiwf.alw.integration.domain.model.validation.AzrNumber;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Component
@Profile("!alw-emulation")
@RequiredArgsConstructor
@Slf4j
public class AlwResponsibilityRestAdapter implements AlwResponsibilityOutPort {

  public static final String FIELD_SACHBEARBEITER = "sachbearbeiter";

  private final RestTemplate restTemplate;
  private final AlwResponsibilityRestConfig alwResponsibilityRestConfig;

  @SuppressWarnings("unchecked")
  @Override
  public Optional<String> getResponsibleSachbearbeiter(@AzrNumber String azrNumber) {
    final String url = constructAlwRequestUrl(azrNumber);
    log.info("Connecting to {} for ALW personen info request", url);
    try {
      final Map<String, String> restResponse = this.restTemplate.getForObject(url, Map.class);
      log.debug("Response from ALW personen info service: {}", restResponse);
      return Optional.ofNullable(restResponse).map(response -> response.get(FIELD_SACHBEARBEITER));
    } catch (Exception ex) {
      if (ex instanceof HttpClientErrorException) {
        HttpClientErrorException cause = (HttpClientErrorException) ex;
        if (HttpStatus.NOT_FOUND.value() == cause.getRawStatusCode()) {
          return Optional.empty();
        }
      }
      throw ex;
    }
  }

  private String constructAlwRequestUrl(@NonNull final String azrNummer) {
    return String.format("%s%s%s", alwResponsibilityRestConfig.getBaseurl(), alwResponsibilityRestConfig.getRestEndpoint(), azrNummer);
  }

}
