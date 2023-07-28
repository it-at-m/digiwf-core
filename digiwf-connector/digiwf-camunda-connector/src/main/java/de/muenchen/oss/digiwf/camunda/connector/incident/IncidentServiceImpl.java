package de.muenchen.oss.digiwf.camunda.connector.incident;


import de.muenchen.oss.digiwf.connector.api.incident.IncidentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.community.rest.client.api.EventSubscriptionApi;
import org.camunda.community.rest.client.api.ExecutionApi;
import org.camunda.community.rest.client.dto.CreateIncidentDto;
import org.camunda.community.rest.client.dto.EventSubscriptionDto;
import org.camunda.community.rest.client.invoker.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class IncidentServiceImpl implements IncidentService {

    private static final String INCIDENT_TYPE   = "integrationError";
    private static final String EVENT_TYPE      = "message";

    private final ExecutionApi executionApi;
    private final EventSubscriptionApi eventSubscriptionApi;

    @Override
    public void createIncident(final String processInstanceId, final String messageName) {
        try {

            //check parameters
            Assert.notNull(processInstanceId, "process instance id cannot be empty");
            Assert.notNull(messageName, "message name cannot be empty");

            //load corresponding event subscription
            final String executionId = this.eventSubscriptionApi.getEventSubscriptions(
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
                    .stream()
                    .findFirst()
                    .map(EventSubscriptionDto::getExecutionId)
                    .orElseThrow();

            // create incident body
            final CreateIncidentDto createIncidentDto = new CreateIncidentDto();
            createIncidentDto.setIncidentType(INCIDENT_TYPE);
            createIncidentDto.setMessage("Error occurred in integration service");

            // send create incident call
            this.executionApi.createIncident(executionId, createIncidentDto);

        } catch (final ApiException e) {
            log.error("Cannot create incident for processinstance id {} and message name {}: {}", processInstanceId, messageName, e.getResponseBody());
            throw new RuntimeException(e);
        } catch (final NoSuchElementException | IllegalArgumentException e) {
            log.error("Cannot create incident for processinstance id {} and message name {}", processInstanceId, messageName);
            throw new RuntimeException(e);
        }

    }
}
