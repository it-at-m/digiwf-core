package io.muenchendigital.digiwf.spring.cloudstream.api.streaming;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
public class ProcessIncomingMessage {

    @Bean
    public Consumer<Message<Object>> processMessage() {
        return message -> log.info(message.toString());
    }

}
