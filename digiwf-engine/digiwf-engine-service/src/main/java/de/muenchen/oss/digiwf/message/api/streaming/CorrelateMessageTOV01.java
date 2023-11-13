package de.muenchen.oss.digiwf.message.api.streaming;

import lombok.*;
import org.springframework.lang.Nullable;

import jakarta.validation.constraints.NotNull;
import java.util.Map;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CorrelateMessageTOV01 {

    @Nullable
    public String processInstanceId;

    @NotNull
    public String messageName;

    @Nullable
    public String businessKey;

    @Nullable
    public Map<String, Object> correlationVariables;

    @Nullable
    public Map<String, Object> correlationVariablesLocal;

    @Nullable
    public Map<String, Object> payloadVariables;

    @Nullable
    public Map<String, Object> payloadVariablesLocal;

}
