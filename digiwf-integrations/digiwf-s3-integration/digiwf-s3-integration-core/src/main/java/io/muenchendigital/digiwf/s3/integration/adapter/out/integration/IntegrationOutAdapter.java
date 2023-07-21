package io.muenchendigital.digiwf.s3.integration.adapter.out.integration;

import io.muenchendigital.digiwf.message.common.MessageConstants;
import io.muenchendigital.digiwf.message.process.api.ErrorApi;
import io.muenchendigital.digiwf.message.process.api.ProcessApi;
import io.muenchendigital.digiwf.message.process.api.error.BpmnError;
import io.muenchendigital.digiwf.message.process.api.error.IncidentError;
import io.muenchendigital.digiwf.s3.integration.application.port.out.IntegrationOutPort;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.lang.NonNull;
import org.springframework.messaging.MessageHeaders;

import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class IntegrationOutAdapter implements IntegrationOutPort {
  private final ProcessApi processApi;
  private final ErrorApi errorApi;

  @Override
  public void correlateProcessMessage(@NonNull MessageHeaders headers, Map<String, Object> payload) {
    final String processInstanceId = Objects.requireNonNull(headers.get(MessageConstants.DIGIWF_PROCESS_INSTANCE_ID)).toString();
    final String messageName = Objects.requireNonNull(headers.get(MessageConstants.DIGIWF_MESSAGE_NAME)).toString();
    if (payload == null) {
      payload = new HashedMap<>();
    }
    this.processApi.correlateMessage(processInstanceId, messageName, payload);
  }

  @Override
  public void handleBpmnError(@NonNull MessageHeaders headers, @NonNull BpmnError bpmnError) {
    this.errorApi.handleBpmnError(headers, bpmnError);
  }

  @Override
  public void handleIncident(@NonNull MessageHeaders headers, @NonNull IncidentError incidentError) {
    this.errorApi.handleIncident(headers, incidentError);
  }
}
