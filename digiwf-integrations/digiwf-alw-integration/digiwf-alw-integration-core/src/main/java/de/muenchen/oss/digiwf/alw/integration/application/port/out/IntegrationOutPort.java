package de.muenchen.oss.digiwf.alw.integration.application.port.out;

import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import org.springframework.lang.NonNull;
import org.springframework.messaging.MessageHeaders;

import java.util.Map;

/**
 * Port to integration infrastructure.
 */
public interface IntegrationOutPort {

  /**
   * Correlates message with a process.
   *
   * @param headers headers describing correlation properties.
   * @param payload payload to pass during correlation.
   */
  void correlateProcessMessage(@NonNull MessageHeaders headers, Map<String, Object> payload);

  /**
   * Handles BPMN error.
   *
   * @param headers   headers for error details.
   * @param bpmnError error to handle.
   */
  void handleBpmnError(@NonNull MessageHeaders headers, @NonNull BpmnError bpmnError);

  /**
   * Handles incident.
   * @param headers headers for error details.
   * @param incidentError incident to handle.
   */
  void handleIncident(@NonNull MessageHeaders headers, @NonNull IncidentError incidentError);
}
