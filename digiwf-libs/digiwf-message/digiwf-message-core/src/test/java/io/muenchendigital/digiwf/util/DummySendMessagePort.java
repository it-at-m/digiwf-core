package io.muenchendigital.digiwf.util;

import io.muenchendigital.digiwf.message.core.api.out.SendMessagePort;
import io.muenchendigital.digiwf.message.core.impl.model.Message;

public class DummySendMessagePort implements SendMessagePort {

    @Override
    public boolean sendMessage(final Message message, final String destination) {
        // whenever a message is sent, return true for testing purposes
        return true;
    }
}
