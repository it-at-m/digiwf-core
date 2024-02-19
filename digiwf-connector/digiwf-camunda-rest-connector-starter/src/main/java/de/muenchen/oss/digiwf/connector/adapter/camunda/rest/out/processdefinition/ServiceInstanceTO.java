package de.muenchen.oss.digiwf.connector.adapter.camunda.rest.out.processdefinition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceInstanceTO {
    private String id;
    private String definitionName;
    private Date startTime;
    private Date endTime;
    private String status;
    private String description;

}
