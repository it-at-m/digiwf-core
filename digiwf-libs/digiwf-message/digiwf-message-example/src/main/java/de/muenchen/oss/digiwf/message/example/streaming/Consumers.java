package de.muenchen.oss.digiwf.message.example.streaming;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Consumer;

@Profile("streaming")
@Slf4j
@Component
public class Consumers {

    @Bean
    public Consumer<Message<Map<String, Object>>> logMessage() {
        return message -> {
            log.info(message.getPayload().toString());
        };
    }

}
