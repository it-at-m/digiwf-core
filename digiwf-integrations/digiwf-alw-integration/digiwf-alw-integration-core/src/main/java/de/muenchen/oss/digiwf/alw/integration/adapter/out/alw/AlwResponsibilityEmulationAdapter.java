package de.muenchen.oss.digiwf.alw.integration.adapter.out.alw;

import de.muenchen.oss.digiwf.alw.integration.application.port.out.AlwResponsibilityOutPort;
import de.muenchen.oss.digiwf.alw.integration.domain.model.validation.AzrNumber;
import de.muenchen.oss.digiwf.alw.integration.infrastructure.SachbearbeitungMapperConfig;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import java.util.Map;
import java.util.Optional;

@Component
@Profile("alw-emulation")
@RequiredArgsConstructor
@Slf4j
public class AlwResponsibilityEmulationAdapter implements AlwResponsibilityOutPort {
  private final AlwResponsibilityRestConfig alwResponsibilityRestConfig;
  @Resource(name = SachbearbeitungMapperConfig.BEAN_ALW_SACHBEARBEITUNG)
  private Map<String, String> sachbearbeitungMap;

  @Override
  public Optional<String> getResponsibleSachbearbeiter(@AzrNumber String azrNumber) {
    final String url = constructAlwRequestUrl(azrNumber);
    log.info("Mocked request to {} for ALW personen info request", url);
    return sachbearbeitungMap.keySet().stream().findFirst();
  }

  private String constructAlwRequestUrl(@NonNull final String azrNummer) {
    return String.format("%s%s%s", alwResponsibilityRestConfig.getBaseurl(), alwResponsibilityRestConfig.getRestEndpoint(), azrNummer);
  }

}
