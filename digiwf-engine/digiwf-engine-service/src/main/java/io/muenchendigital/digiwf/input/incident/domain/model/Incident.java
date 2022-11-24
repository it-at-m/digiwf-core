package io.muenchendigital.digiwf.input.incident.domain.model;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Incident {

    @NotNull
    public String processInstanceId;

    @NotNull
    public String messageName;

    @Nullable
    public String errorMessage;

}
