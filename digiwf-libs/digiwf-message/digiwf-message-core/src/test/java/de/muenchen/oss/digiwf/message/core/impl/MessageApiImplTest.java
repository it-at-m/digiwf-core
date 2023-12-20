package de.muenchen.oss.digiwf.message.core.impl;


import de.muenchen.oss.digiwf.message.core.api.MessageApi;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.messaging.Message;
import reactor.core.publisher.Sinks;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MessageApiImplTest {

    private final Sinks.Many<org.springframework.messaging.Message<Object>> messageSink = Mockito.spy(Mockito.mock(Sinks.Many.class));
    @InjectMocks
    private final MessageApi messageApi = new MessageApiImpl(this.messageSink);

    @Test
    void testSendMessage() {
        final ArgumentCaptor<Message<Object>> messageCaptor = ArgumentCaptor.forClass(Message.class);
        when(this.messageSink.tryEmitNext(messageCaptor.capture())).thenReturn(Sinks.EmitResult.OK);

        final boolean success = this.messageApi.sendMessage("test", "test");
        assertThat(success).isTrue();

        // check message object that should be sent
        assertThat(messageCaptor.getValue().getPayload()).isEqualTo("test");
        assertThat(messageCaptor.getValue().getHeaders().get("spring.cloud.stream.sendto.destination")).isEqualTo("test");
    }

    @Test
    void testSendMessageFails() {
        when(this.messageSink.tryEmitNext(any(Message.class))).thenReturn(Sinks.EmitResult.FAIL_TERMINATED);
        final boolean success = this.messageApi.sendMessage("test", "test");
        assertThat(success).isFalse();
    }

    @Test
    void testSendMessageWithCustomHeaders() {
        final ArgumentCaptor<Message<Object>> messageCaptor = ArgumentCaptor.forClass(Message.class);
        when(this.messageSink.tryEmitNext(messageCaptor.capture())).thenReturn(Sinks.EmitResult.OK);

        final Map<String, Object> headers = Map.of("key", "value");
        final boolean success = this.messageApi.sendMessage("test", headers, "test");
        assertThat(success).isTrue();

        // check message object that should be sent
        assertThat(messageCaptor.getValue().getPayload()).isEqualTo("test");
        assertThat(messageCaptor.getValue().getHeaders().get("spring.cloud.stream.sendto.destination")).isEqualTo("test");
        assertThat(messageCaptor.getValue().getHeaders().get("key")).isEqualTo("value");
    }

    @Test
    void testSendMessageFailsIfDestinationisNull() {
        boolean success = this.messageApi.sendMessage("test", null);
        assertThat(success).isFalse();
        verify(this.messageSink, Mockito.never()).tryEmitNext(any(Message.class));

        // with headers
        success = this.messageApi.sendMessage("test", Map.of("test", "test"), null);
        assertThat(success).isFalse();
        verify(this.messageSink, Mockito.never()).tryEmitNext(any(Message.class));
    }

    @Test
    void testSendMessageFailsIfPayloadIsNull() {
        boolean success = this.messageApi.sendMessage(null, "test");
        assertThat(success).isFalse();
        verify(this.messageSink, Mockito.never()).tryEmitNext(any(Message.class));

        // with headers
        success = this.messageApi.sendMessage(null, Map.of("test", "test"), "test");
        assertThat(success).isFalse();
        verify(this.messageSink, Mockito.never()).tryEmitNext(any(Message.class));
    }

}
