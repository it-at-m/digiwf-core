package de.muenchen.oss.digiwf.connector.rest.incident;

import lombok.Data;

@Data
public class CreateIncidentDto {

    private String processInstanceId;

    private String messageName;

}
