package de.muenchen.oss.digiwf.connector.adapter.out;


import de.muenchen.oss.digiwf.connector.adapter.camunda.rest.out.IncidentAdapter;
import org.camunda.community.rest.client.api.EventSubscriptionApi;
import org.camunda.community.rest.client.api.ExecutionApi;
import org.camunda.community.rest.client.dto.CreateIncidentDto;
import org.camunda.community.rest.client.dto.EventSubscriptionDto;
import org.camunda.community.rest.client.invoker.ApiException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

@DisplayName("Incident Service Test")
public class IncidentAdapterTest {

    private final ExecutionApi executionApi = mock(ExecutionApi.class);

    private final EventSubscriptionApi eventSubscriptionApi = mock(EventSubscriptionApi.class);

    private final IncidentAdapter incidentService = new IncidentAdapter(this.executionApi, this.eventSubscriptionApi);

    @Test
    @DisplayName("should create incident with default error message")
    public void shouldCreateIncidentWithDefaultErrorMessage() throws ApiException {

        final EventSubscriptionDto eventSubscriptionDto = new EventSubscriptionDto();
        eventSubscriptionDto.setExecutionId("executionId");

        final CreateIncidentDto expectedCreateIncidentDto = new CreateIncidentDto();
        expectedCreateIncidentDto.setIncidentType("integrationError");
        expectedCreateIncidentDto.setMessage("Error occurred in integration service");

        when(this.eventSubscriptionApi.getEventSubscriptions(
                null,
                "messageName",
                "message",
                null,
                "instanceId",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null)).thenReturn(List.of(eventSubscriptionDto));

        this.incidentService.createIncident("instanceId", "messageName", null);

        verify(this.eventSubscriptionApi).getEventSubscriptions(
                null,
                "messageName",
                "message",
                null,
                "instanceId",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);

        verify(this.executionApi).createIncident("executionId", expectedCreateIncidentDto);
    }

    @Test
    @DisplayName("should create incident with default error message when message body is blank")
    public void shouldCreateIncidentWithDefaultErrorMessageWhenMessageBodyIsBlank() throws ApiException {

        final EventSubscriptionDto eventSubscriptionDto = new EventSubscriptionDto();
        eventSubscriptionDto.setExecutionId("executionId");

        final CreateIncidentDto expectedCreateIncidentDto = new CreateIncidentDto();
        expectedCreateIncidentDto.setIncidentType("integrationError");
        expectedCreateIncidentDto.setMessage("Error occurred in integration service");

        when(this.eventSubscriptionApi.getEventSubscriptions(
                null,
                "messageName",
                "message",
                null,
                "instanceId",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null)).thenReturn(List.of(eventSubscriptionDto));

        this.incidentService.createIncident("instanceId", "messageName", " ");

        verify(this.eventSubscriptionApi).getEventSubscriptions(
                null,
                "messageName",
                "message",
                null,
                "instanceId",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);

        verify(this.executionApi).createIncident("executionId", expectedCreateIncidentDto);
    }

    @Test
    @DisplayName("should create incident with given error message")
    public void shouldCreateIncidentWithGivenErrorMessage() throws ApiException {

        final EventSubscriptionDto eventSubscriptionDto = new EventSubscriptionDto();
        eventSubscriptionDto.setExecutionId("executionId");

        final CreateIncidentDto expectedCreateIncidentDto = new CreateIncidentDto();
        expectedCreateIncidentDto.setIncidentType("integrationError");
        expectedCreateIncidentDto.setMessage("my-error-message");

        when(this.eventSubscriptionApi.getEventSubscriptions(
                null,
                "messageName",
                "message",
                null,
                "instanceId",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null)).thenReturn(List.of(eventSubscriptionDto));

        this.incidentService.createIncident("instanceId", "messageName", "my-error-message");

        verify(this.eventSubscriptionApi).getEventSubscriptions(
                null,
                "messageName",
                "message",
                null,
                "instanceId",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);

        verify(this.executionApi).createIncident("executionId", expectedCreateIncidentDto);
    }
}
