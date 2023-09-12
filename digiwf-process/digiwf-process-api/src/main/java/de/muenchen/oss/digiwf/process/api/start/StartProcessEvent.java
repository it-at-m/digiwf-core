package de.muenchen.oss.digiwf.process.api.start;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * Kafka Event for starting a process
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StartProcessEvent implements StartProcess {

    /**
     * Key of the process
     */
    @NotBlank
    private String key;

    /**
     * File context for document storage
     */
    private String fileContext;

    /**
     * Data that is set
     */
    private Map<String, Object> data;

}
