package de.muenchen.oss.digiwf.camunda.connector.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "de.muenchen.oss.digiwf.camunda.connector")
public class DigiWFCamundaConnectorProperties {

    private List<String> filterVariables;


}
