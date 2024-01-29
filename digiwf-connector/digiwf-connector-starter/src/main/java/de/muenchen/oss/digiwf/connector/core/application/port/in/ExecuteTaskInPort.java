package de.muenchen.oss.digiwf.connector.core.application.port.in;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Map;

public interface ExecuteTaskInPort {

    void executeTask(@Valid ExecuteTaskCommand command);

    @Data
    @ToString
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class ExecuteTaskCommand {
        private String messageName;

        @NotBlank
        private String destination;

        @NotBlank
        private String type;

        @NotBlank
        private String instanceId;

        private Map<String, Object> data;
    }

}
