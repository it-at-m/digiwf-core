package io.muenchendigital.digiwf.message.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

/**
 * Configuration properties for the digiwf-message library.
 *
 * If you want to use the digiwf-message library, you need to add the following properties to your application.yml:
 * - io.muenchendigital.digiwf.message.incidentDestination: <your incident destination>
 * - io.muenchendigital.digiwf.message.technicalErrorDestination: <your technical error destination>
 * - io.muenchendigital.digiwf.message.correlateMessageDestination: <your correlate message destination>
 * - io.muenchendigital.digiwf.message.startProcessDestination: <your start process destination>
 */
@Setter
@Getter
@Validated
@ConfigurationProperties(prefix = "io.muenchendigital.digiwf.message")
public class DigiwfMessageProperties {
    private String incidentDestination;
    private String bpmnErrorDestination;
    private String correlateMessageDestination;
    private String startProcessDestination;
    private Map<String, String> typeMappings;
}
