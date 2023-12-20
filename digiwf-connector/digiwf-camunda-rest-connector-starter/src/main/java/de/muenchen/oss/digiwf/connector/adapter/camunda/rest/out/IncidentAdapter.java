package de.muenchen.oss.digiwf.connector.adapter.camunda.rest.out;

import de.muenchen.oss.digiwf.connector.core.application.port.in.CreateIncidentInPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.community.rest.client.api.EventSubscriptionApi;
import org.camunda.community.rest.client.api.ExecutionApi;
import org.camunda.community.rest.client.model.CreateIncidentDto;
import org.camunda.community.rest.client.model.EventSubscriptionDto;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class IncidentAdapter implements CreateIncidentInPort {

    private static final String INCIDENT_TYPE = "integrationError";
    private static final String EVENT_TYPE = "message";

    private final ExecutionApi executionApi;
    private final EventSubscriptionApi eventSubscriptionApi;

    @Override
    public void createIncident(final String processInstanceId, final String messageName, final String messageContent) {
        try {

            //check parameters
            Assert.notNull(processInstanceId, "process instance id cannot be empty");
            Assert.notNull(messageName, "message name cannot be empty");

            //load corresponding event subscription
            final List<EventSubscriptionDto> eventSubscriptions = this.eventSubscriptionApi.getEventSubscriptions(
                            null,
                            messageName,
                            EVENT_TYPE,
                            null,
                            processInstanceId,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null)
                    .getBody();
            if (eventSubscriptions.isEmpty()) {
                throw new NoSuchElementException();
            }
            final String executionId = eventSubscriptions.stream()
                    .findFirst()
                    .map(EventSubscriptionDto::getExecutionId)
                    .orElseThrow();

            // create incident body
            final CreateIncidentDto createIncidentDto = new CreateIncidentDto();
            createIncidentDto.setIncidentType(INCIDENT_TYPE);
            createIncidentDto.setMessage(
                    messageContent != null && !messageContent.isBlank()
                            ? messageContent
                            : "Error occurred in integration service"
            );

            // send create incident call
            this.executionApi.createIncident(executionId, createIncidentDto);
        } catch (final NoSuchElementException | IllegalArgumentException e) {
            log.error("Cannot create incident for processinstance id {} and message name {}", processInstanceId, messageName);
            throw new RuntimeException(e);
        }

    }
}
