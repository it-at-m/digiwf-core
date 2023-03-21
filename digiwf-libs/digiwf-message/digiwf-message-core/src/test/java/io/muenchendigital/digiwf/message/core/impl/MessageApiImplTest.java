package io.muenchendigital.digiwf.message.core.impl;


import io.muenchendigital.digiwf.message.core.impl.model.Message;
import io.muenchendigital.digiwf.util.DummySendMessagePort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.util.Map;

class MessageApiImplTest {
    @Spy
    private DummySendMessagePort spyDummySendMessagePort = Mockito.spy(new DummySendMessagePort());
    private final MessageApiImpl messageApi = new MessageApiImpl(this.spyDummySendMessagePort);

    @Test
    void testSendMessage() {
        final boolean success = this.messageApi.sendMessage("test", "test");
        Assertions.assertTrue(success);

        // check message object that should be sent
        final ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);
        Mockito.verify(this.spyDummySendMessagePort).sendMessage(messageCaptor.capture(), Mockito.eq("test"));
        Assertions.assertEquals("test", messageCaptor.getValue().getPayload());
        Assertions.assertTrue(messageCaptor.getValue().getHeaders().containsKey("type"));
    }

    @Test
    void testSendMessageWithCustomHeaders() {
        final Map<String, Object> headers = Map.of("key", "value");
        final boolean success = this.messageApi.sendMessage("test", headers, "test");
        Assertions.assertTrue(success);

        // check message object that should be sent
        final ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);
        Mockito.verify(this.spyDummySendMessagePort).sendMessage(messageCaptor.capture(), Mockito.eq("test"));
        Assertions.assertEquals("test", messageCaptor.getValue().getPayload());
        Assertions.assertTrue(messageCaptor.getValue().getHeaders().containsKey("type"));
        Assertions.assertEquals("value", messageCaptor.getValue().getHeaders().get("key"));
    }

    @Test
    void testSendMessageFailsIfDestinationisNull() {
        boolean success = this.messageApi.sendMessage("test", null);
        Assertions.assertFalse(success);

        // with headers
        success = this.messageApi.sendMessage("test", Map.of("test", "test"), null);
        Assertions.assertFalse(success);
    }

    @Test
    void testSendMessageFailsIfPayloadIsNull() {
        boolean success = this.messageApi.sendMessage(null, "test");
        Assertions.assertFalse(success);

        // with headers
        success = this.messageApi.sendMessage(null, Map.of("test", "test"), "test");
        Assertions.assertFalse(success);
    }

}
