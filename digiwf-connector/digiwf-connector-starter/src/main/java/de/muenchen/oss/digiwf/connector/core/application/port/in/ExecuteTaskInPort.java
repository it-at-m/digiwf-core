package de.muenchen.oss.digiwf.connector.core.application.port.in;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

public interface ExecuteTaskInPort {

    void executeTask(ExecuteTaskCommand command);

    @Data
    @ToString
    @Builder
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
