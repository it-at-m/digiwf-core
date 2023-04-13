package io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.infrastructure;

import io.muenchendigital.digiwf.connector.api.bpmnerror.BpmnErrorEvent;
import io.muenchendigital.digiwf.connector.api.message.CorrelateMessageEvent;
import io.muenchendigital.digiwf.process.api.StartProcessEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Configuration
@Deprecated
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

    /**
     * Sink for sending correlate Messages
     *
     * @return Sink
     */
    @Deprecated
    @Bean
    public Sinks.Many<Message<CorrelateMessageEvent>> sendCorrelateMessageSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    /**
     * Supplier for sending correlateMessages
     *
     * @param sink corresponding sink
     * @return supplier
     */
    @Deprecated
    @Bean
    public Supplier<Flux<Message<CorrelateMessageEvent>>> sendCorrelateMessage(final Sinks.Many<Message<CorrelateMessageEvent>> sink) {
        return sink::asFlux;
    }

    /**
     * Sink for sending errors
     *
     * @return Sink
     */
    @Deprecated
    @Bean
    public Sinks.Many<Message<BpmnErrorEvent>> sendBpmnErrorSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    /**
     * Supplier for sending errors
     *
     * @param sink corresponding sink
     * @return supplier
     */
    @Deprecated
    @Bean
    public Supplier<Flux<Message<BpmnErrorEvent>>> sendBpmnError(final Sinks.Many<Message<BpmnErrorEvent>> sink) {
        return sink::asFlux;
    }

    /**
     * Sink for sending incidents
     *
     * @return Sink
     */
    @Deprecated
    @Bean
    public Sinks.Many<Message<String>> sendIncidentSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    /**
     * Supplier for sending incidents
     *
     * @param sink corresponding sink
     * @return supplier
     */
    @Deprecated
    @Bean
    public Supplier<Flux<Message<String>>> sendIncident(final Sinks.Many<Message<String>> sink) {
        return sink::asFlux;
    }

    /**
     * Sink for sending start process events
     *
     * @return Sink
     */
    @Deprecated
    @Bean
    public Sinks.Many<Message<StartProcessEvent>> startProcessSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    /**
     * Supplier for sending start process events
     *
     * @param sink corresponding sink
     * @return supplier
     */
    @Deprecated
    @Bean
    public Supplier<Flux<Message<StartProcessEvent>>> sendStartProcess(final Sinks.Many<Message<StartProcessEvent>> sink) {
        return sink::asFlux;
    }

}
