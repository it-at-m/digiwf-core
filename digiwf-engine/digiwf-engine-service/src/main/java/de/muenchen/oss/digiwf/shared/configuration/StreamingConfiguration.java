package de.muenchen.oss.digiwf.shared.configuration;

import de.muenchen.oss.digiwf.shared.streaming.StreamingHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.function.context.MessageRoutingCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class StreamingConfiguration {

    private final StreamingProperties streamingProperties;

    @Bean
    public MessageRoutingCallback customRouter() {
        return new MessageRoutingCallback() {

            @Override
            public FunctionRoutingResult routingResult(final Message<?> message) {
                final String route = StreamingConfiguration.this.streamingProperties.getTypeMappings().get((String) message.getHeaders().get(StreamingHeaders.TYPE));
                // if there is no route specified send the message to emptyRoute
                // to avoid errors if an event cannot be processed
                if (StringUtils.isBlank(route)) {
                    return new FunctionRoutingResult("emptyRoute");
                }
                return new FunctionRoutingResult(route);
            }
        };
    }

    @Bean
    public Consumer<Message<?>> emptyRoute() {
        return message -> {
            log.debug("Message of type {} should not be processed", message.getHeaders().get(StreamingHeaders.TYPE));
        };
    }

}
