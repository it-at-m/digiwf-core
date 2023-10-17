package de.muenchen.oss.digiwf.connector.rest.incident;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class CreateIncidentDto {

    private String processInstanceId;

    private String messageName;

    @Nullable
    private String messageContent;

}
