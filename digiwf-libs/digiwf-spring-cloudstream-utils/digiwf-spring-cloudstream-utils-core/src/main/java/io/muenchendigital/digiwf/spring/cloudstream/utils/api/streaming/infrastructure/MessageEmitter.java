package io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.infrastructure;

import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.message.dto.CorrelateMessageDto;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.bpmnerror.dto.BpmnErrorDto;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.incident.dto.IncidentDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Configuration
public class MessageEmitter {

    /**
     * Sink for sending messages
     * @return Sink
     */
    @Bean
    public Sinks.Many<Message<Object>> sendMessageSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    /**
     * Supplier for sending messages
     * @param sink corresponding sink
     * @return Supplier
     */
    @Bean
    public Supplier<Flux<Message<Object>>> sendMessage(final Sinks.Many<Message<Object>> sink) {
        return sink::asFlux;
    }

    /**
     * Sink for sending correlate Messages
     * @return Sink
     */
    @Bean
    public Sinks.Many<Message<CorrelateMessageDto>> sendCorrelateMessageSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    /**
     * Supplier for sending correlateMessages
     * @param sink corresponding sink
     * @return supplier
     */
    @Bean
    public Supplier<Flux<Message<CorrelateMessageDto>>> sendCorrelateMessage(final Sinks.Many<Message<CorrelateMessageDto>> sink) {
        return sink::asFlux;
    }

    /**
     * Sink for sending errors
     * @return Sink
     */
    @Bean
    public Sinks.Many<Message<BpmnErrorDto>> sendBpmnErrorSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    /**
     * Supplier for sending errors
     * @param sink corresponding sink
     * @return supplier
     */
    @Bean
    public Supplier<Flux<Message<BpmnErrorDto>>> sendBpmnError(final Sinks.Many<Message<BpmnErrorDto>> sink) {
        return sink::asFlux;
    }

    /**
     * Sink for sending errors
     * @return Sink
     */
    @Bean
    public Sinks.Many<Message<IncidentDto>> sendIncidentSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    /**
     * Supplier for sending errors
     * @param sink corresponding sink
     * @return supplier
     */
    @Bean
    public Supplier<Flux<Message<IncidentDto>>> sendIncident(final Sinks.Many<Message<IncidentDto>> sink) {
        return sink::asFlux;
    }

}
