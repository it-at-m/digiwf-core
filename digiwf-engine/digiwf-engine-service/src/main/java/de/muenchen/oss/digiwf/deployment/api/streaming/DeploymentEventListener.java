package de.muenchen.oss.digiwf.deployment.api.streaming;

import io.muenchendigital.digiwf.asyncapi.docs.annotations.DocumentAsyncAPI;
import de.muenchen.oss.digiwf.deployment.api.mapper.DeploymentMapper;
import de.muenchen.oss.digiwf.deployment.api.streaming.event.DeploymentEvent;
import de.muenchen.oss.digiwf.deployment.domain.model.DeploymentStatusModel;
import de.muenchen.oss.digiwf.deployment.domain.service.ModelDeploymentService;
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
public class DeploymentEventListener {

    private final Sinks.Many<Message<DeploymentStatusModel>> statusEmitter;
    private final ModelDeploymentService modelDeploymentService;
    private final DeploymentMapper mapper;

    @DocumentAsyncAPI(payload = DeploymentEvent.class, functionRouter = true, typeHeader = "deploy")
    @Bean
    public Consumer<Message<DeploymentEvent>> deploy() {
        return message -> {
            final DeploymentEvent deploymentEvent = message.getPayload();
            log.info("Sent deployment event with deploymentId: {}, versionId: {}, target: {}, artifactType: {}", deploymentEvent.getDeploymentId(), deploymentEvent.getVersionId(), deploymentEvent.getTarget(), deploymentEvent.getArtifactType());
            // trigger deployment
            final DeploymentStatusModel status = this.modelDeploymentService.deploy(this.mapper.mapToDeploymentModel(deploymentEvent));

            final Message<DeploymentStatusModel> statusMessage = MessageBuilder
                    .withPayload(status)
                    .build();

            this.statusEmitter.tryEmitNext(statusMessage).orThrow();
            log.debug("Sent deployment status event for deployment {}", status.getDeploymentId());
        };
    }

}
