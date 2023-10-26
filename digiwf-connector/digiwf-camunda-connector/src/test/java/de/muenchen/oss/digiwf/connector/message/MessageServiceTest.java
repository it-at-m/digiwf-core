package de.muenchen.oss.digiwf.connector.message;


import de.muenchen.oss.digiwf.camunda.connector.data.EngineDataSerializer;
import de.muenchen.oss.digiwf.camunda.connector.message.MessageServiceImpl;
import de.muenchen.oss.digiwf.connector.BaseSpringTest;
import de.muenchen.oss.digiwf.connector.api.message.CorrelateMessage;
import de.muenchen.oss.digiwf.connector.api.message.MessageService;
import de.muenchen.oss.digiwf.connector.message.internal.impl.model.CorrelateMessageImpl;
import org.camunda.community.rest.client.api.MessageApi;
import org.camunda.community.rest.client.invoker.ApiException;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

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

    @Order(1)
    @Test
    @DisplayName("should correlate message")
    public void shouldCorrelateMessage() throws ApiException {

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
