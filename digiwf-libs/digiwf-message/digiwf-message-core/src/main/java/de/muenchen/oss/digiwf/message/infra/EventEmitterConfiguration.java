package de.muenchen.oss.digiwf.message.infra;

import de.muenchen.oss.digiwf.message.common.MessageConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * EventEmitter configuration that creates the sinks for sending messages
 * Note: We use the spring cloud stream header spring.cloud.stream.sendto.destination to send messages dynamically to topics.
 * Therefore, we only need a single message sink.
 */
@Configuration
@Slf4j
public class EventEmitterConfiguration {

    /**
     * Sink for sending messages
     *
     * @return Sink
     */
    @Bean
    public Sinks.Many<Message<Object>> sendMessageSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    /**
     * Supplier for sending messages
     *
     * @param sink corresponding sink
     * @return Supplier
     */
    @Bean
    public Supplier<Flux<Message<Object>>> sendMessage(final Sinks.Many<Message<Object>> sink) {
        return sink::asFlux;
    }

    public static final String FUNCTION_ROUTING_ERROR = "springCloudstreamUtilsFunctionRoutingError";
    public static final String MISSING_TYPE_HEADER_ERROR = "springCloudstreamUtilsMissingTypeHeaderError";

    /**
     * When TYPE header of the incoming message is set but not configured in the application, message gets routed here.
     * @return Consumer
     */
    @Bean
    public Consumer<Message<Object>> springCloudstreamUtilsFunctionRoutingError() {
        return message -> log.error("Message handling for messages with type '{}' is not implemented. (message {})", message.getHeaders().get(MessageConstants.TYPE), message.getHeaders().get(MessageHeaders.ID));
    }

    /**
     * When TYPE header of the incoming message is unset, message gets routed here.
     * @return Consumer
     */
    @Bean
    public Consumer<Message<Object>> springCloudstreamUtilsMissingTypeHeaderError() {
        return message -> log.error("The message header '{}' must be set in message '{}'.", MessageConstants.TYPE, message.getHeaders().get(MessageHeaders.ID));
    }

}
