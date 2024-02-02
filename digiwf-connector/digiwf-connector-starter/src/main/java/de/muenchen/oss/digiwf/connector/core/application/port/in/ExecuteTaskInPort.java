package de.muenchen.oss.digiwf.connector.core.application.port.in;

import de.muenchen.oss.digiwf.connector.core.domain.IntegrationNameConfigException;
import de.muenchen.oss.digiwf.connector.core.domain.ProcessDefinitionLoadingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Map;

public interface ExecuteTaskInPort {

    void executeTask(@Valid ExecuteTaskCommand command) throws IntegrationNameConfigException, ProcessDefinitionLoadingException;

    @Data
    @ToString
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class ExecuteTaskCommand {

        private String customDestination;

        @NotBlank
        private String integrationName;

        @NotBlank
        private String type;

        @NotBlank
        private String instanceId;

        private Map<String, Object> data;
    }

}
