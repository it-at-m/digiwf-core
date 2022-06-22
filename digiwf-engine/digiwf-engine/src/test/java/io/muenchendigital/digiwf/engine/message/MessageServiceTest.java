package io.muenchendigital.digiwf.engine.message;


import io.muenchendigital.digiwf.BaseSpringTest;
import io.muenchendigital.digiwf.engine.data.EngineDataSerializer;
import io.muenchendigital.digiwf.engine.message.api.CorrelateMessage;
import io.muenchendigital.digiwf.engine.message.api.MessageService;
import io.muenchendigital.digiwf.engine.message.internal.model.CorrelateMessageImpl;
import io.muenchendigital.digiwf.engine.message.internal.service.MessageServiceImpl;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.MessageCorrelationBuilder;
import org.camunda.community.mockito.ProcessExpressions;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.Map;

import static org.mockito.Mockito.verify;

@DisplayName("Message Service Test")
@Import({EngineDataSerializer.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MessageServiceTest extends BaseSpringTest {

    @Mock
    private RuntimeService runtimeService;

    @Autowired
    private EngineDataSerializer engineDataSerializer;

    private MessageService messageService;

    @BeforeEach
    private void initTests() {
        this.messageService = new MessageServiceImpl(this.runtimeService, this.engineDataSerializer);
    }

    @Order(1)
    @Test
    @DisplayName("shouldCorrelateMessage")
    public void shouldCorrelateMessage() {

        final CorrelateMessage correlateMessage = CorrelateMessageImpl.builder()
                .messageName("myMessage")
                .processInstanceId("myId")
                .businessKey("businessKey")
                .payloadVariables(Map.of("key", "value"))
                .payloadVariablesLocal(Map.of("localKey", "localValue"))
                .build();

        final MessageCorrelationBuilder correlation = ProcessExpressions.mockMessageCorrelation(this.runtimeService, "myMessage");

        this.messageService.correlateMessage(correlateMessage);

        verify(correlation).correlate();
        verify(correlation).processInstanceId("myId");
        verify(correlation).processInstanceBusinessKey("businessKey");
        verify(correlation).setVariables(Map.of("key", "value"));
        verify(correlation).setVariablesLocal(Map.of("localKey", "localValue"));
    }

}
