package io.muenchendigital.digiwf.spring.cloudstream.utils.api.error;

import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.infrastructure.StreamingHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
public class ErrorMessageDefaultListener {

    public static final String FUNCTION_ROUTING_ERROR = "springCloudstreamUtilsFunctionRoutingError";
    public static final String MISSING_TYPE_HEADER_ERROR = "springCloudstreamUtilsMissingTypeHeaderError";

    /**
     * When TYPE header of the incoming message is set but not configured in the application, message gets routed here.
     * @return Consumer
     */
    @Bean
    public Consumer<Message<Object>> springCloudstreamUtilsFunctionRoutingError() {
        return message -> log.error("Message handling for messages with type '{}' is not implemented. (message {})", message.getHeaders().get(StreamingHeaders.TYPE), message.getHeaders().get(MessageHeaders.ID));
    }

    /**
     * When TYPE header of the incoming message is unset, message gets routed here.
     * @return Consumer
     */
    @Bean
    public Consumer<Message<Object>> springCloudstreamUtilsMissingTypeHeaderError() {
        return message -> log.error("The message header '{}' must be set in message '{}'.", StreamingHeaders.TYPE, message.getHeaders().get(MessageHeaders.ID));
    }

}
