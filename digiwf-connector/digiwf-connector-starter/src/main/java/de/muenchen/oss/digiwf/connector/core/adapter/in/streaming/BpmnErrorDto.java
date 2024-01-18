package de.muenchen.oss.digiwf.connector.core.adapter.in.streaming;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BpmnErrorDto {

    private String processInstanceId;

    private String messageName;

    private String errorCode;

    private String errorMessage;

}
