/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.alw.integration.adapter.in.streaming;

import de.muenchen.oss.digiwf.alw.integration.application.port.in.GetResponsibilityInPort;
import de.muenchen.oss.digiwf.alw.integration.application.port.out.IntegrationOutPort;
import de.muenchen.oss.digiwf.alw.integration.domain.exception.AlwException;
import de.muenchen.oss.digiwf.alw.integration.domain.model.Responsibility;
import de.muenchen.oss.digiwf.alw.integration.domain.model.ResponsibilityRequest;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Map;
import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageProcessor {

    private static final String ALW_ZUSTAENDIGE_GRUPPE = "alwZustaendigeGruppe";

    private final IntegrationOutPort integration;
    private final GetResponsibilityInPort getResponsibilityInPort;

    /**
     * This method handles all messages from the route "getAlwResponsibility".
     * <p>
     * The consumer can throw the following BPMN error codes:
     * <ul>
     *   <li>{@link AlwErrorCodes#UNEXPECTED_ERROR}: If the responsibility could not be found, either because it was not returned by ALW or the returned one does not match any known responsibility.</li>
     *   <li>{@link AlwErrorCodes#VALIDATION_ERROR_CODE}: If the requested AZR-Number is not valid. It must consist of 12 digits.</li>
     *   <li>{@link AlwErrorCodes#UNEXPECTED_ERROR}: If ALW responds with an unexpected error code.</li>
     * </ul>
     * </p>
     *
     * @return the consumer that handles the messages from the route "getAlwResponsibility"
     */
    @Bean
    public Consumer<Message<ResponsibilityRequest>> getAlwResponsibility() {
        return message -> {
            log.info("Processing new request from eventbus");
            final ResponsibilityRequest request = message.getPayload();
            val headers = message.getHeaders();
            log.debug("Request: {}", request);
            try {
                final Responsibility response = getResponsibilityInPort.getResponsibility(request);
                final Map<String, Object> result = Map.of(ALW_ZUSTAENDIGE_GRUPPE, response.getOrgUnit());
                integration.correlateProcessMessage(headers, result);
            } catch (final HttpStatusCodeException httpStatusCodeException) {
                integration.handleBpmnError(headers,
                        new BpmnError(AlwErrorCodes.UNEXPECTED_ERROR.toString(), httpStatusCodeException.getResponseBodyAsString()));
            } catch (final ConstraintViolationException cve) {
                integration.handleBpmnError(headers, new BpmnError(AlwErrorCodes.VALIDATION_ERROR_CODE.toString(), cve.getMessage()));
            } catch (final AlwException alwException) {
                integration.handleBpmnError(headers, new BpmnError(AlwErrorCodes.RESPONSIBILITY_NOT_FOUND.toString(), alwException.getMessage()));
            } catch (final Exception e) {
                log.error("Request could not be fulfilled", e);
                integration.handleIncident(headers, new IncidentError(e.getMessage()));
            }
        };
    }
}
