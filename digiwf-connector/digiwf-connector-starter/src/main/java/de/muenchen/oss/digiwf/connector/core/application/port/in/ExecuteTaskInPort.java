package de.muenchen.oss.digiwf.connector.core.application.port.in;

import de.muenchen.oss.digiwf.connector.core.domain.IntegrationNameConfigException;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Map;

public interface ExecuteTaskInPort {

    void executeTask(ExecuteTaskCommand command) throws IntegrationNameConfigException;

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
