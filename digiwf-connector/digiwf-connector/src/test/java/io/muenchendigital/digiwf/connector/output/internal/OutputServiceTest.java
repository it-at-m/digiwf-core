package io.muenchendigital.digiwf.connector.output.internal;


import io.muenchendigital.digiwf.connector.BaseSpringTest;
import io.muenchendigital.digiwf.connector.data.EngineDataSerializer;
import io.muenchendigital.digiwf.connector.output.api.OutputService;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import reactor.core.publisher.Sinks;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Streaming Service Test")
@Import({EngineDataSerializer.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OutputServiceTest extends BaseSpringTest {

    @Autowired
    private EngineDataSerializer engineDataSerializer;

    @Mock
    private Sinks.Many<Message<Map<String, Object>>> dynamicSink;

    private OutputService outputService;

    @BeforeEach
    private void initTests() {
        this.outputService = new OutputServiceImpl(this.engineDataSerializer, this.dynamicSink);
    }

    @Order(1)
    @Test
    @DisplayName("shouldEmitMessage")
    public void shouldEmitMessage() {

        when(this.dynamicSink.tryEmitNext(any())).thenReturn(Sinks.EmitResult.OK);

        this.outputService.emitEvent("myTopic", "myType", "myInstance", Map.of("key", "value"));

        verify(this.dynamicSink, times(1)).tryEmitNext(any());
    }

}
