package de.muenchen.oss.digiwf.connector.adapter.camunda.rest.in;

import de.muenchen.oss.digiwf.connector.adapter.camunda.rest.mapper.EngineDataSerializer;
import de.muenchen.oss.digiwf.connector.core.application.port.in.ExecuteTaskInPort;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CamundaClientTest {

    @Mock
    private ExecuteTaskInPort executeTaskInPort;

    @Mock
    private CamundaClientConfiguration clientConfiguration;

    @Mock
    private EngineDataSerializer serializer;

    @Mock
    private ExternalTask externalTask;

    @Mock
    private ExternalTaskService externalTaskService;

    @InjectMocks
    private CamundaClient camundaClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testExecute() {
        // Prepare test data
        VariableMap testData = new VariableMapImpl();
        testData.put(CamundaClientConfiguration.TOPIC_NAME, "testTopic");
        testData.put(CamundaClientConfiguration.TYPE_NAME, "testType");
        testData.put(CamundaClientConfiguration.MESSAGE_NAME, "testMessage");
        testData.put("someOtherKey", "someOtherValue");

        HashMap<String, Object> filteredData = new HashMap<>();
        filteredData.put("someOtherKey", "someOtherValue");

        // Configure mocks
        ExecuteTaskInPort.ExecuteTaskCommand expectedCommand = ExecuteTaskInPort.ExecuteTaskCommand.builder()
                .destination("testTopic")
                .type("testType")
                .messageName("testMessage")
                .instanceId("testProcessInstanceId")
                .data(filteredData)
                .build();

        when(externalTask.getProcessInstanceId()).thenReturn("testProcessInstanceId");
        when(externalTask.getAllVariablesTyped()).thenReturn(testData);
        when(serializer.fromEngineData(testData)).thenReturn(testData);
        when(clientConfiguration.getFilters()).thenReturn(List.of(
                CamundaClientConfiguration.TOPIC_NAME,
                CamundaClientConfiguration.TYPE_NAME,
                CamundaClientConfiguration.MESSAGE_NAME));

        // Execute the method under test
        camundaClient.execute(externalTask, externalTaskService);

        // Verify interactions and assert conditions
        verify(executeTaskInPort).executeTask(expectedCommand);
        verify(externalTaskService).complete(externalTask);
    }
}
