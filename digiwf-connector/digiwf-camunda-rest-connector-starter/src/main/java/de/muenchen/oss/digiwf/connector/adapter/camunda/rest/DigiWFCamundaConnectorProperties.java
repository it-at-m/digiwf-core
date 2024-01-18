package de.muenchen.oss.digiwf.connector.adapter.camunda.rest;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "de.muenchen.oss.digiwf.connector.adapter.camunda.rest")
public class DigiWFCamundaConnectorProperties {

    private List<String> filterVariables;


}
