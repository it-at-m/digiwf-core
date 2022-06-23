package io.muenchendigital.digiwf.enginestreaming.jsonschema;

import de.muenchen.digitalwf.engine.deployment.domain.model.DeploymentStatusModel;
import de.muenchen.digitalwf.jsonschema.deployment.api.mapper.SchemaDeploymentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

import java.util.function.Consumer;


@Slf4j
@Component
@RequiredArgsConstructor
public class SchemaDeploymentEventConsumer {

    private final Sinks.Many<Message<DeploymentStatusModel>> statusEmitter;

    private final SchemaDeploymentService modelDeploymentService;
    
    private final SchemaDeploymentMapper mapper;

    @Bean
    public Consumer<Message<SchemaDeploymentEvent>> deploySchema() {
        return message -> {
            final SchemaDeploymentEvent deploymentEvent = message.getPayload();
            log.info("Sent deployment event with deploymentId: {}, versionId: {}, target: {}, artifactType: {}", deploymentEvent.getDeploymentId(), deploymentEvent.getVersionId(), deploymentEvent.getTarget(), deploymentEvent.getArtifactType());
            // trigger deployment
            final DeploymentStatusModel status = this.modelDeploymentService.deploy(this.mapper.mapTo(deploymentEvent));

            final Message<DeploymentStatusModel> statusMessage = MessageBuilder
                    .withPayload(status)
                    .build();

            this.statusEmitter.tryEmitNext(statusMessage);
            log.info("Sent deployment status event for deployment {}", status.getDeploymentId());
        };
    }
}
