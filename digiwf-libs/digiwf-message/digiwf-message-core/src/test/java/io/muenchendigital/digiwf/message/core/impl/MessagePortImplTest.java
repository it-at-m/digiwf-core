package io.muenchendigital.digiwf.message.core.impl;

import io.muenchendigital.digiwf.message.core.impl.model.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import reactor.core.publisher.Sinks;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class MessagePortImplTest {

    private final Sinks.Many<org.springframework.messaging.Message<Object>> messageSink = Mockito.spy(Mockito.mock(Sinks.Many.class));
    @InjectMocks
    private final MessagePortImpl messagePort = new MessagePortImpl(this.messageSink);

    private final String destination = "test";
    private Message msg;

    @BeforeEach
    void setUp() {
        this.msg = new Message();
        this.msg.addHeader("test", "qwerty");
        this.msg.addPayload("asdf");
    }

    @Test
    void testSendMessageIsSuccessful() {
        when(this.messageSink.tryEmitNext(any())).thenReturn(Sinks.EmitResult.OK);

        final boolean success = this.messagePort.sendMessage(this.msg, this.destination);
        Assertions.assertTrue(success);

        final ArgumentCaptor<org.springframework.messaging.Message<Object>> messageCaptor = ArgumentCaptor.forClass(org.springframework.messaging.Message.class);
        Mockito.verify(this.messageSink).tryEmitNext(messageCaptor.capture());
        Assertions.assertEquals("asdf", messageCaptor.getValue().getPayload());
        Assertions.assertTrue(messageCaptor.getValue().getHeaders().containsKey("spring.cloud.stream.sendto.destination"));
        Assertions.assertEquals(this.destination, messageCaptor.getValue().getHeaders().get("spring.cloud.stream.sendto.destination"));
        Assertions.assertTrue(messageCaptor.getValue().getHeaders().containsKey("test"));
        Assertions.assertEquals("qwerty", messageCaptor.getValue().getHeaders().get("test"));
    }

    @Test
    void testSendMessageFails() {
        when(this.messageSink.tryEmitNext(any())).thenReturn(Sinks.EmitResult.FAIL_TERMINATED);
        final boolean success = this.messagePort.sendMessage(this.msg, this.destination);
        Assertions.assertFalse(success);
    }

}
