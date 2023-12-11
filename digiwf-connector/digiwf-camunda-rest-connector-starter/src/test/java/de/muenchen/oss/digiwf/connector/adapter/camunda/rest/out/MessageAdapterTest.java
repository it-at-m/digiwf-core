package de.muenchen.oss.digiwf.connector.adapter.camunda.rest.out;


import de.muenchen.oss.digiwf.connector.adapter.BaseSpringTest;
import de.muenchen.oss.digiwf.connector.adapter.camunda.rest.mapper.EngineDataSerializer;
import de.muenchen.oss.digiwf.connector.core.application.port.out.CorrelateMessageOutPort;
import de.muenchen.oss.digiwf.connector.core.domain.MessageCorrelation;
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
public class MessageAdapterTest extends BaseSpringTest {

    @Mock
    private MessageApi messageApi;

    @Autowired
    private EngineDataSerializer engineDataSerializer;

    private CorrelateMessageOutPort messageService;

    @BeforeEach
    private void initTests() {
        this.messageService = new MessageAdapter(this.messageApi, this.engineDataSerializer);
    }

    @Order(1)
    @Test
    @DisplayName("should correlate message")
    public void shouldCorrelateMessage() throws ApiException {

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
