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
public class AlwMessageProcessor {

    private static final String ALW_ZUSTAENDIGE_GRUPPE = "alwZustaendigeGruppe";

    private final IntegrationOutPort integration;
    private final GetResponsibilityInPort getResponsibilityInPort;

    /**
     * All messages from the route "getAlwResponsibility" go here.
     *
     * @return the consumer
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
                integration.handleBpmnError(headers, new BpmnError(AlwErrorCodes.OTHER.toString(), httpStatusCodeException.getResponseBodyAsString()));
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
