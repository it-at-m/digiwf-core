package io.muenchendigital.digiwf.engine.streaming.internal;


import io.muenchendigital.digiwf.engine.BaseSpringTest;
import io.muenchendigital.digiwf.engine.data.EngineDataSerializer;
import io.muenchendigital.digiwf.engine.streaming.api.StreamingService;
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
public class StreamingServiceTest extends BaseSpringTest {

    @Autowired
    private EngineDataSerializer engineDataSerializer;

    @Mock
    private Sinks.Many<Message<Map<String, Object>>> dynamicSink;

    private StreamingService streamingService;

    @BeforeEach
    private void initTests() {
        this.streamingService = new StreamingServiceImpl(this.engineDataSerializer, this.dynamicSink);
    }

    @Order(1)
    @Test
    @DisplayName("shouldEmitMessage")
    public void shouldEmitMessage() {

        when(this.dynamicSink.tryEmitNext(any())).thenReturn(Sinks.EmitResult.OK);

        this.streamingService.emitMessage("myTopic", "myType", "myInstance", Map.of("key", "value"));

        verify(this.dynamicSink, times(1)).tryEmitNext(any());
    }

}
