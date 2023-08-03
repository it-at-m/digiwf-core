package de.muenchen.oss.digiwf.connector.incident;


import de.muenchen.oss.digiwf.camunda.connector.incident.IncidentServiceImpl;
import de.muenchen.oss.digiwf.connector.BaseSpringTest;
import de.muenchen.oss.digiwf.connector.api.incident.IncidentService;
import org.camunda.community.rest.client.api.EventSubscriptionApi;
import org.camunda.community.rest.client.api.ExecutionApi;
import org.camunda.community.rest.client.dto.CreateIncidentDto;
import org.camunda.community.rest.client.dto.EventSubscriptionDto;
import org.camunda.community.rest.client.invoker.ApiException;
import org.junit.jupiter.api.*;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Incident Service Test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IncidentServiceTest extends BaseSpringTest {

    @Mock
    private ExecutionApi executionApi;

    @Mock
    private EventSubscriptionApi eventSubscriptionApi;

    private IncidentService incidentService;

    @BeforeEach
    private void initTests() {
        this.incidentService = new IncidentServiceImpl(this.executionApi, this.eventSubscriptionApi);
    }

    @Order(1)
    @Test
    @DisplayName("should create incident")
    public void shouldCreateIncident() throws ApiException {

        final EventSubscriptionDto eventSubscriptionDto = new EventSubscriptionDto();
        eventSubscriptionDto.setExecutionId("executionId");

        final CreateIncidentDto createIncidentDto = new CreateIncidentDto();
        createIncidentDto.setIncidentType("integrationError");
        createIncidentDto.setMessage("Error occurred in integration service");

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

        this.incidentService.createIncident("instanceId", "messageName");

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

        verify(this.executionApi).createIncident("executionId", createIncidentDto);
    }

}
