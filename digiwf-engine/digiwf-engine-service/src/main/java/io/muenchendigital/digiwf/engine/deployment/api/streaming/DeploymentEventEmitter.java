package io.muenchendigital.digiwf.engine.deployment.api.streaming;

import io.muenchendigital.digiwf.engine.deployment.domain.model.DeploymentStatusModel;
import io.muenchendigital.digiwf.asyncapi.docs.annotations.DocumentAsyncAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Configuration
public class DeploymentEventEmitter {

    @Bean
    public Sinks.Many<Message<DeploymentStatusModel>> deploymentStatusSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @DocumentAsyncAPI(payload = DeploymentStatusModel.class)
    @Bean
    public Supplier<Flux<Message<DeploymentStatusModel>>> deploymentStatus(final Sinks.Many<Message<DeploymentStatusModel>> sink) {
        return sink::asFlux;
    }
}
