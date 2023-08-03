/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.process.instance.domain.model;

import de.muenchen.oss.digiwf.process.config.domain.model.ProcessConfig;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ServiceInstanceDetail {

    /**
     * Id of the service instance info object.
     */
    private final String id;

    /**
     * Id of the process instance.
     */
    private String instanceId;

    /**
     * Name of the corresponding process definition.
     */
    private final String definitionName;

    /**
     * Start time
     */
    private final Date startTime;

    /**
     * End time
     */
    private final Date endTime;

    /**
     * Status
     */
    private final String status;

    /**
     * Description provides further information
     */
    private final String description;

    /**
     * Key of the status
     */
    private final String statusKey;

    /**
     * Config of the corresponding process definition.
     */
    private ProcessConfig processConfig;

    /**
     * Tasks that have been completed so far.
     */
    private List<HistoryTask> historyTasks;

    /**
     * Provided data of the instance.
     */
    private Map<String, Object> data;

    /**
     * json schema
     */
    private Map<String, Object> jsonSchema;


    public void setConfig(final ProcessConfig config) {
        this.processConfig = config;
    }

    public void setHistoryTasks(final List<HistoryTask> historyTasks) {
        this.historyTasks = historyTasks;
    }

    public void setData(final Map<String, Object> data) {
        this.data = data;
    }

    public void setJsonSchema(final Map<String, Object> jsonSchema) {
        this.jsonSchema = jsonSchema;
    }

}
