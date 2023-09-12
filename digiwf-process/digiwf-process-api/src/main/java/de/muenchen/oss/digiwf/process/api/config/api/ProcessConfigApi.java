package de.muenchen.oss.digiwf.process.api.config.api;

import de.muenchen.oss.digiwf.process.api.config.api.dto.ProcessConfigTO;

/**
 * ProcessConfigApi is the api to obtain the process configuration.
 */
public interface ProcessConfigApi {

    /**
     * Obtain a process configuration for a given process definition id.
     *
     * @param processDefinitionId the process definition id
     * @return the process configuration
     */
    ProcessConfigTO getProcessConfig(final String processDefinitionId);

}
