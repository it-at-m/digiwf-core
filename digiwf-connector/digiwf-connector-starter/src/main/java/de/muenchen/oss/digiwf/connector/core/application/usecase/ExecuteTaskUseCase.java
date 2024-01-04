package de.muenchen.oss.digiwf.connector.core.application.usecase;

import de.muenchen.oss.digiwf.connector.core.DigiWFConnectorProperties;
import de.muenchen.oss.digiwf.connector.core.application.port.in.ExecuteTaskInPort;
import de.muenchen.oss.digiwf.connector.core.application.port.out.EmitEventOutPort;
import de.muenchen.oss.digiwf.connector.core.domain.IntegrationNameConfigException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@Validated
@RequiredArgsConstructor
class ExecuteTaskUseCase implements ExecuteTaskInPort {

    private final EmitEventOutPort emitEventOutPort;
    private final DigiWFConnectorProperties digiWFConnectorProperties;

    @Override
    public void executeTask(@Valid ExecuteTaskCommand command) throws IntegrationNameConfigException {
        log.info("Execute task with command {}", command);

        final String destination = StringUtils.isNotBlank(command.getCustomDestination()) ?
                command.getCustomDestination() :
                this.getDefaultDestination(command.getIntegrationName());

        emitEventOutPort.emitEvent(destination, command.getType(), command.getInstanceId(), command.getIntegrationName(), command.getData());
    }

    private String getDefaultDestination(String integrationName) {
        if (!digiWFConnectorProperties.getIntegrations().containsKey(integrationName)) {
            throw new IntegrationNameConfigException("Integration " + integrationName + " is not registered in configuration.", integrationName);
        }
        return digiWFConnectorProperties.getIntegrations().get(integrationName);
    }
}
