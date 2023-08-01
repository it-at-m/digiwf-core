package io.muenchendigital.digiwf.message.example.message.service;

import io.muenchendigital.digiwf.message.core.api.MessageApi;
import io.muenchendigital.digiwf.message.example.message.dto.Message;
import io.muenchendigital.digiwf.message.example.message.dto.MessageSuccess;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageApi sendMessageApi;

    private final String messageDestination = "dwf-message-example";

    public MessageSuccess sendMessage(final Message message) {
        // send a message to the destination
        final boolean success = this.sendMessageApi.sendMessage(message, this.messageDestination);
            return new MessageSuccess(success, success ? "Message was successfully sent": "Sending message failed");
    }

}
