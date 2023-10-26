package de.muenchen.oss.digiwf.connector.output.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.function.Supplier;

@Slf4j
@Component
@RequiredArgsConstructor
public class DynamicOutputEventEmitter {

    @Bean
    public Sinks.Many<Message<Map<String, Object>>> dynamicSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Supplier<Flux<Message<Map<String, Object>>>> dynamicProducer(final Sinks.Many<Message<Map<String, Object>>> sink) {
        return sink::asFlux;
    }
}
