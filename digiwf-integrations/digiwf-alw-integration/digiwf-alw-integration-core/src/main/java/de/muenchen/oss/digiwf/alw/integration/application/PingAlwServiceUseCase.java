package de.muenchen.oss.digiwf.alw.integration.application;

import de.muenchen.oss.digiwf.alw.integration.application.port.in.PingAwlServiceInPort;
import de.muenchen.oss.digiwf.alw.integration.application.port.out.AlwResponsibilityOutPort;
import de.muenchen.oss.digiwf.alw.integration.domain.model.AlwPingConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Slf4j
public class PingAlwServiceUseCase implements PingAwlServiceInPort {

  private final AlwResponsibilityOutPort alwResponsibilityOutPort;
  private final AlwPingConfig alwPingConfiguration;

  @PostConstruct
  void executeOnStartup() {
    pingService();
  }

  @Override
  public void pingService() {
    if (alwPingConfiguration.isPingEnabled()) {
      alwResponsibilityOutPort.getResponsibleSachbearbeiter(alwPingConfiguration.getPingAzrNumber());
      log.info("Ping to ALW Service successful.");
    }
  }
}
