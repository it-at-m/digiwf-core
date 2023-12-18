package de.muenchen.oss.digiwf.connector.message;


import de.muenchen.oss.digiwf.camunda.connector.data.EngineDataSerializer;
import de.muenchen.oss.digiwf.camunda.connector.message.MessageServiceImpl;
import de.muenchen.oss.digiwf.connector.api.message.CorrelateMessage;
import de.muenchen.oss.digiwf.connector.api.message.MessageService;
import de.muenchen.oss.digiwf.connector.message.internal.impl.model.CorrelateMessageImpl;
import org.camunda.community.rest.client.api.MessageApi;
import org.junit.jupiter.api.*;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("Message Service Test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MessageServiceTest {
    private final MessageApi messageApi = mock(MessageApi.class);

    private final EngineDataSerializer engineDataSerializer = new EngineDataSerializer();

    private MessageService messageService;

    @BeforeEach
    void initTests() {
        this.messageService = new MessageServiceImpl(this.messageApi, this.engineDataSerializer);
    }

    @Order(1)
    @Test
    @DisplayName("should correlate message")
    void shouldCorrelateMessage() {

        final CorrelateMessage correlateMessage = CorrelateMessageImpl.builder()
                .messageName("myMessage")
                .processInstanceId("myId")
                .businessKey("businessKey")
                .payloadVariables(Map.of("key", "value"))
                .payloadVariablesLocal(Map.of("localKey", "localValue"))
                .build();

        this.messageService.correlateMessage(correlateMessage);

        verify(this.messageApi).deliverMessage(any());
    }
}
