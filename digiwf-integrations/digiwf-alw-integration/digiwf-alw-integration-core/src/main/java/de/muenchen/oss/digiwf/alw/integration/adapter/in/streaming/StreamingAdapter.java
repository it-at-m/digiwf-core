/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.alw.integration.adapter.in.streaming;

import de.muenchen.oss.digiwf.alw.integration.application.port.in.GetResponsibilityInPort;
import de.muenchen.oss.digiwf.alw.integration.domain.exception.AlwException;
import de.muenchen.oss.digiwf.alw.integration.domain.model.Responsibility;
import de.muenchen.oss.digiwf.alw.integration.domain.model.ResponsibilityRequest;
import de.muenchen.oss.digiwf.message.common.MessageConstants;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
public class StreamingAdapter {

    private static final String ALW_ZUSTAENDIGE_GRUPPE = "alwZustaendigeGruppe";

    private final ProcessApi processApi;
    private final ErrorApi errorApi;
    private final GetResponsibilityInPort getResponsibilityInPort;

    /**
     * This method handles all messages from the route "getAlwResponsibility".
     * <p>
     * The consumer can throw the following BPMN error codes:
     * <ul>
     *   <li>{@link AlwErrorCodes#RESPONSIBILITY_NOT_FOUND}: If the responsibility could not be found, either because it was not returned by ALW or the returned one does not match any known responsibility.</li>
     *   <li>{@link AlwErrorCodes#VALIDATION_ERROR_CODE}: If the requested AZR-Number is not valid. It must consist of 12 digits.</li>
     *   <li>{@link AlwErrorCodes#UNEXPECTED_ERROR}: If ALW responds with an unexpected error code.</li>
     * </ul>
     * </p>
     *
     * @return the consumer that handles the messages from the route "getAlwResponsibility"
     */
    public Consumer<Message<ResponsibilityRequest>> getAlwResponsibility() {
        return message -> {
            log.info("Processing new request from eventbus");
            final ResponsibilityRequest request = message.getPayload();
            val headers = message.getHeaders();
            log.debug("Request: {}", request);
            try {
                final Responsibility response = getResponsibilityInPort.getResponsibility(request);
                final Map<String, Object> result = Map.of(ALW_ZUSTAENDIGE_GRUPPE, response.getOrgUnit());
                this.correlateProcessMessage(headers, result);
            } catch (final HttpStatusCodeException httpStatusCodeException) {
                errorApi.handleBpmnError(headers,
                        new BpmnError(AlwErrorCodes.UNEXPECTED_ERROR.toString(), httpStatusCodeException.getResponseBodyAsString()));
            } catch (final ConstraintViolationException cve) {
                errorApi.handleBpmnError(headers, new BpmnError(AlwErrorCodes.VALIDATION_ERROR_CODE.toString(), cve.getMessage()));
            } catch (final AlwException alwException) {
                errorApi.handleBpmnError(headers, new BpmnError(AlwErrorCodes.RESPONSIBILITY_NOT_FOUND.toString(), alwException.getMessage()));
            } catch (final Exception e) {
                log.error("Request could not be fulfilled", e);
                errorApi.handleIncident(headers, new IncidentError(e.getMessage()));
            }
        };
    }

    public void correlateProcessMessage(@NonNull MessageHeaders headers, Map<String, Object> payload) {
        final String processInstanceId = Objects.requireNonNull(headers.get(MessageConstants.DIGIWF_PROCESS_INSTANCE_ID)).toString();
        final String integrationName = Objects.requireNonNull(headers.get(MessageConstants.DIGIWF_INTEGRATION_NAME)).toString();
        final String type = Objects.requireNonNull(headers.get(MessageConstants.TYPE)).toString();
        if (payload == null) {
            payload = new HashMap<>();
        }
        this.processApi.correlateMessage(processInstanceId, type, integrationName, payload);
    }
}
