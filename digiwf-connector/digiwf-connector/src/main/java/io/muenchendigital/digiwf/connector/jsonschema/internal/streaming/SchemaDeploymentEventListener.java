package io.muenchendigital.digiwf.connector.jsonschema.internal.streaming;

import io.muenchendigital.digiwf.connector.jsonschema.api.DeploymentStatusModel;
import io.muenchendigital.digiwf.connector.jsonschema.api.SchemaDeploymentEvent;
import io.muenchendigital.digiwf.connector.jsonschema.internal.deployment.SchemaDeploymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

import java.util.function.Consumer;


@Slf4j
@Component
@RequiredArgsConstructor
public class SchemaDeploymentEventListener {

    private final Sinks.Many<Message<DeploymentStatusModel>> statusEmitter;
    private final SchemaDeploymentService modelDeploymentService;

    @Bean
    public Consumer<Message<SchemaDeploymentEvent>> deploySchema() {
        return message -> {
            final SchemaDeploymentEvent deploymentEvent = message.getPayload();
            log.info("Sent deployment event with deploymentId: {}, versionId: {}, target: {}, artifactType: {}", deploymentEvent.getDeploymentId(), deploymentEvent.getVersionId(), deploymentEvent.getTarget(), deploymentEvent.getArtifactType());

            final DeploymentStatusModel status = this.modelDeploymentService.deploy(deploymentEvent.getDeploymentId(), deploymentEvent.getVersionId(), deploymentEvent.getFile());

            final Message<DeploymentStatusModel> statusMessage = MessageBuilder
                    .withPayload(status)
                    .build();

            this.statusEmitter.tryEmitNext(statusMessage);
            log.info("Sent deployment status event for deployment {}", status.getDeploymentId());
        };
    }
}
