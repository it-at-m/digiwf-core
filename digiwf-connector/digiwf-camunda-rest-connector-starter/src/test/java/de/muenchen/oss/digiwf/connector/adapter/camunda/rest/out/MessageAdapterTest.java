package de.muenchen.oss.digiwf.connector.message;


import de.muenchen.oss.digiwf.connector.adapter.camunda.rest.mapper.EngineDataSerializer;
import de.muenchen.oss.digiwf.connector.adapter.camunda.rest.out.MessageAdapter;
import de.muenchen.oss.digiwf.connector.core.application.port.out.CorrelateMessageOutPort;
import de.muenchen.oss.digiwf.connector.core.domain.MessageCorrelation;
import org.camunda.community.rest.client.api.MessageApi;
import org.junit.jupiter.api.*;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("Message Service Test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MessageAdapterTest {
    private final MessageApi messageApi = mock(MessageApi.class);

    private final EngineDataSerializer engineDataSerializer = new EngineDataSerializer();

    private CorrelateMessageOutPort messageService;

    @BeforeEach
    void initTests() {
        this.messageService = new MessageAdapter(this.messageApi, this.engineDataSerializer);
    }

    @Order(1)
    @Test
    @DisplayName("should correlate message")
    void shouldCorrelateMessage() {

        final MessageCorrelation messageCorrelation = MessageCorrelation.builder()
                .messageName("myMessage")
                .processInstanceId("myId")
                .businessKey("businessKey")
                .payloadVariables(Map.of("key", "value"))
                .payloadVariablesLocal(Map.of("localKey", "localValue"))
                .build();

        this.messageService.correlateMessage(messageCorrelation);

        verify(this.messageApi).deliverMessage(any());
    }
}
