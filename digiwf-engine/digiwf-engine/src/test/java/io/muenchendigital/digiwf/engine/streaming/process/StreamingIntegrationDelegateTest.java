package io.muenchendigital.digiwf.engine.streaming.process;

import io.muenchendigital.digiwf.engine.BaseSpringTest;
import io.muenchendigital.digiwf.engine.streaming.api.StreamingService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.junit.jupiter.api.*;
import org.mockito.Mock;

import java.util.Map;

import static org.camunda.community.mockito.CamundaMockito.delegateExecutionFake;
import static org.mockito.Mockito.*;

@DisplayName("Streaming Service Test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StreamingIntegrationDelegateTest extends BaseSpringTest {

    @Mock
    private StreamingService streamingService;

    private StreamingIntegrationDelegate streamingIntegrationDelegate;

    @BeforeEach
    private void initTests() {
        this.streamingIntegrationDelegate = new StreamingIntegrationDelegate(this.streamingService);
    }

    @Order(1)
    @Test
    @DisplayName("shouldExecuteDelegate")
    public void shouldExecuteDelegate() throws Exception {

        doNothing().when(this.streamingService).emitMessage("myTopic", "myType", "myInstance", Map.of("key", "value"));

        final DelegateExecution execution = delegateExecutionFake()
                .withVariablesLocal(Map.of(
                        "app_topic_name", "myTopic",
                        "app_type_name", "myType",
                        "key", "value"
                ))
                .withProcessInstanceId("myInstance");

        this.streamingIntegrationDelegate.execute(execution);

        verify(this.streamingService, times(1)).emitMessage("myTopic", "myType", "myInstance", Map.of("key", "value"));
    }

    @Order(1)
    @Test
    @DisplayName("shouldExecuteDelegateWithMessage")
    public void shouldExecuteDelegateWithMessage() throws Exception {

        doNothing().when(this.streamingService).emitMessage("myMessage", "myTopic", "myType", "myInstance", Map.of("key", "value"));

        final DelegateExecution execution = delegateExecutionFake()
                .withVariablesLocal(Map.of(
                        "app_topic_name", "myTopic",
                        "app_type_name", "myType",
                        "app_message_name", "myMessage",
                        "key", "value"
                ))
                .withProcessInstanceId("myInstance");

        this.streamingIntegrationDelegate.execute(execution);

        verify(this.streamingService, times(1)).emitMessage("myMessage", "myTopic", "myType", "myInstance", Map.of("key", "value"));
    }
}
