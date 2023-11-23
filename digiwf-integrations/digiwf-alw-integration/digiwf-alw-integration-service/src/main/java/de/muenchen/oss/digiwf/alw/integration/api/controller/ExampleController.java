package de.muenchen.oss.digiwf.alw.integration.api.controller;

import de.muenchen.oss.digiwf.alw.integration.application.port.in.GetResponsibilityInPort;
import de.muenchen.oss.digiwf.alw.integration.domain.exception.AlwException;
import de.muenchen.oss.digiwf.alw.integration.domain.model.Responsibility;
import de.muenchen.oss.digiwf.alw.integration.domain.model.ResponsibilityRequest;
import de.muenchen.oss.digiwf.message.core.api.MessageApi;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.internalServerError;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Controller as an api for the {@link GetResponsibilityInPort } without streaming infrastructure.
 */
@RestController
@Profile({"local", "itest"})
@RequiredArgsConstructor
@Slf4j
public class ExampleController {

  private final GetResponsibilityInPort getResponsibility;
  private final MessageApi messageApi;

  @PostConstruct
  public void reportExistence() {
    log.warn("|----------------------------------------");
    log.warn("|");
    log.warn("| Example Test Controller is initialized");
    log.warn("| Don't use this in production");
    log.warn("|");
    log.warn("|----------------------------------------");
  }

  @GetMapping(value = "/getAlwZustaendigkeit/{azrNumber}", produces = "text/plain;charset=UTF-8")
  public ResponseEntity<String> getAlwZustaendigkeit(@PathVariable("azrNumber") final String azrNumber) throws AlwException {
    log.debug("Incoming request");
    Responsibility response;
    try {
      response = getResponsibility.getResponsibility(new ResponsibilityRequest(azrNumber));
    } catch (final AlwException ex) {
      log.error("Request failed", ex);
      return internalServerError().build();
    }
    log.info("Request successful");
    return ok(response.getOrgUnit());
  }

  @GetMapping(value = "/getAlwZustaendigkeitEventBus")
  public void testGetAlwZustaendigkeitEventBus() {
    val headers = new HashMap<String, Object>();
    headers.put("type", "getAlwResponsibility");
    headers.put("digiwf.messagename", "message1");
    headers.put("digiwf.processinstanceid", UUID.randomUUID().toString());
    messageApi.sendMessage(
        new ResponsibilityRequest("123456789012"),
        headers,
        "dwf-alw-local-01");
  }

}
