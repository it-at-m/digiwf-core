package io.muenchendigital.digiwf.message.example.adapter;

import io.muenchendigital.digiwf.message.core.api.out.SendMessagePort;
import io.muenchendigital.digiwf.message.core.impl.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SendMessageAdapter implements SendMessagePort {

    @Override
    public boolean sendMessage(final Message message, final String destination) {
        log.info("Message was successfully sent to {}", destination);
        log.info("Message payload was {}", message.getPayload().toString());
        return true;
    }
}
