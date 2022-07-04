package io.muenchendigital.digiwf.connector.message;


import io.muenchendigital.digiwf.camunda.connector.data.EngineDataSerializer;
import io.muenchendigital.digiwf.camunda.connector.message.MessageServiceImpl;
import io.muenchendigital.digiwf.connector.BaseSpringTest;
import io.muenchendigital.digiwf.connector.message.api.MessageService;
import org.camunda.community.rest.client.api.MessageApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@DisplayName("Message Service Test")
@Import({EngineDataSerializer.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MessageServiceTest extends BaseSpringTest {

    @Mock
    private MessageApi messageApi;

    @Autowired
    private EngineDataSerializer engineDataSerializer;

    private MessageService messageService;

    @BeforeEach
    private void initTests() {
        this.messageService = new MessageServiceImpl(this.messageApi, this.engineDataSerializer);
    }

//    @Order(1)
//    @Test
//    @DisplayName("shouldCorrelateMessage")
//    public void shouldCorrelateMessage() {
//
//        final CorrelateMessage correlateMessage = CorrelateMessageImpl.builder()
//                .messageName("myMessage")
//                .processInstanceId("myId")
//                .businessKey("businessKey")
//                .payloadVariables(Map.of("key", "value"))
//                .payloadVariablesLocal(Map.of("localKey", "localValue"))
//                .build();
//
//        final MessageCorrelationBuilder correlation = ProcessExpressions.mockMessageCorrelation(this.messageApi, "myMessage");
//
//        this.messageService.correlateMessage(correlateMessage);
//
//        verify(correlation).correlate();
//        verify(correlation).processInstanceId("myId");
//        verify(correlation).processInstanceBusinessKey("businessKey");
//        verify(correlation).setVariables(Map.of("key", "value"));
//        verify(correlation).setVariablesLocal(Map.of("localKey", "localValue"));
//    }

}
