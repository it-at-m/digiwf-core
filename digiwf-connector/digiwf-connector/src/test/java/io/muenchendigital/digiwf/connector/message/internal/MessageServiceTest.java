package io.muenchendigital.digiwf.connector.message.internal;


import io.muenchendigital.digiwf.connector.BaseSpringTest;
import io.muenchendigital.digiwf.connector.message.api.MessageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

@DisplayName("Message Service Test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MessageServiceTest extends BaseSpringTest {


    private MessageService messageService;

//    @BeforeEach
//    private void initTests() {
//        this.messageService = new MessageServiceImpl(this.runtimeService, this.engineDataSerializer);
//    }
//
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
//        final MessageCorrelationBuilder correlation = ProcessExpressions.mockMessageCorrelation(this.runtimeService, "myMessage");
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
