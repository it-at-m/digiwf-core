package io.muenchendigital.digiwf.connectorrest.incident;

import lombok.Data;

@Data
public class CreateIncidentDto {

    private String processInstanceId;

    private String messageName;

}
