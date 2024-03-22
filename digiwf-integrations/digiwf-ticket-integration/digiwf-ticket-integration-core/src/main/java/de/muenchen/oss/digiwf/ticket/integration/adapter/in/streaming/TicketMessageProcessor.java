/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.ticket.integration.adapter.in.streaming;

import de.muenchen.oss.digiwf.message.common.MessageConstants;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import de.muenchen.oss.digiwf.ticket.integration.application.port.in.WriteArticleInPort;
import de.muenchen.oss.digiwf.ticket.integration.domain.model.Article;
import de.muenchen.oss.digiwf.ticket.integration.domain.model.TicketStatus;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_PROCESS_DEFINITION;

@Slf4j
@RequiredArgsConstructor
public class TicketMessageProcessor {

    private static final String VALIDATION_ERROR_CODE = "VALIDATION_ERROR";

    private final WriteArticleInPort writeArticleInPort;

    private final ProcessApi processApi;

    private final ErrorApi errorApi;

    /**
     * All messages from the route "getAlwResponsibility" go here.
     *
     * @return the consumer
     */
    public Consumer<Message<WriteArticleDto>> writeArticle() {
        return message -> {
            log.info("Processing new request from eventbus");
            val request = message.getPayload();
            val headers = message.getHeaders();
            log.debug("Request: {}", request);
            try {
                writeArticleInPort.writeArticle(
                        request.getTicketId(), new Article(request.getArticle(), request.getUserId()),
                        mapStatus(request.getStatus()), request.getFilepaths(), headers.get(DIGIWF_PROCESS_DEFINITION).toString());
                correlateProcessMessage(headers, Map.of());
            } catch (ConstraintViolationException cve) {
                handleBpmnError(headers, new BpmnError(VALIDATION_ERROR_CODE, cve.getMessage()));
            } catch (final Exception e) {
                log.error("Request could not be fulfilled", e);
                handleIncident(headers, new IncidentError(e.getMessage()));
            }
        };
    }

    private TicketStatus mapStatus(String status) {
        if (StringUtils.isBlank(status)) {
            return null;
        }
        if (status.equalsIgnoreCase("-")) {
            return null;
        }
        return TicketStatus.valueOf(status);
    }

    private void correlateProcessMessage(@NonNull MessageHeaders headers, Map<String, Object> payload) {
        final String processInstanceId = Objects.requireNonNull(headers.get(MessageConstants.DIGIWF_PROCESS_INSTANCE_ID)).toString();
        final String integrationName = Objects.requireNonNull(headers.get(MessageConstants.DIGIWF_INTEGRATION_NAME)).toString();
        final String type = Objects.requireNonNull(headers.get(MessageConstants.TYPE)).toString();
        if (payload == null) {
            payload = new HashMap<>();
        }
        this.processApi.correlateMessage(processInstanceId, type, integrationName, payload);
    }

    private void handleBpmnError(@NonNull MessageHeaders headers, @NonNull BpmnError bpmnError) {
        this.errorApi.handleBpmnError(headers, bpmnError);
    }

    private void handleIncident(@NonNull MessageHeaders headers, @NonNull IncidentError incidentError) {
        this.errorApi.handleIncident(headers, incidentError);
    }
}
