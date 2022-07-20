package io.muenchendigital.digiwf.camunda.connector.incident;


import io.muenchendigital.digiwf.camunda.connector.data.EngineDataSerializer;
import io.muenchendigital.digiwf.connector.incident.api.IncidentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.community.rest.client.api.EventSubscriptionApi;
import org.camunda.community.rest.client.api.ExecutionApi;
import org.camunda.community.rest.client.dto.CreateIncidentDto;
import org.camunda.community.rest.client.dto.EventSubscriptionDto;
import org.camunda.community.rest.client.invoker.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import java.util.NoSuchElementException;

@Slf4j
@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class IncidentServiceImpl implements IncidentService {

    private final ExecutionApi executionApi;
    private final EventSubscriptionApi eventSubscriptionApi;
    private final EngineDataSerializer serializer;

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
                            "message",
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
            createIncidentDto.setIncidentType("integrationError");
            createIncidentDto.setMessage("Error occurred in integration artifact");

            // send create incident call
            this.executionApi.createIncident(executionId, createIncidentDto);

        } catch (final ApiException | NoSuchElementException | IllegalArgumentException e) {
            log.error("Cannot create incident for processinstance id {} and message name {}", processInstanceId, messageName);
        }

    }
}
