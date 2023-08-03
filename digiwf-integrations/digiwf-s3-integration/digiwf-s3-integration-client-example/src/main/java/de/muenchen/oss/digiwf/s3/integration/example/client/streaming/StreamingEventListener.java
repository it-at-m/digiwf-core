package de.muenchen.oss.digiwf.s3.integration.example.client.streaming;

import de.muenchen.oss.digiwf.s3.integration.example.client.streaming.events.CorrelateMessageTOV01;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.Message;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Consumer;

/**
 * Spring cloud stream event listener that gets {@link CorrelateMessageTOV01} messages for demo purposes
 *
 * @author ext.dl.moesle
 */
@Profile("streaming")
@Slf4j
@Configuration
public class StreamingEventListener {

    /**
     * Spring cloud stream consumer that prints all incoming {@link CorrelateMessageTOV01} events to stout for testing purposes
     */
    @SuppressWarnings("unchecked")
    @Bean
    public Consumer<Message<CorrelateMessageTOV01>> correlatemessagev01() {
        return message -> {
            final CorrelateMessageTOV01 correlateMessageEvent = message.getPayload();
            if (correlateMessageEvent.getPayloadVariables().containsKey("presignedUrls")) {
                // unchecked cast
                final List<LinkedHashMap<String, String>> presignedUrls = (List<LinkedHashMap<String, String>>) correlateMessageEvent.getPayloadVariables().get("presignedUrls");
                presignedUrls
                        .forEach(presignedUrl -> {
                            log.info("Path: {} - Action: {} - PresginedUrl: {}", presignedUrl.get("path"), presignedUrl.get("action"), presignedUrl.get("url"));
                            // sout for easier testing
                            System.out.println(presignedUrl.get("url"));
                        });
            }
        };
    }
}
