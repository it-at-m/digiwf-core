package de.muenchen.oss.digiwf.process.api.config.impl;

import de.muenchen.oss.digiwf.process.api.config.api.ProcessConfigApi;
import de.muenchen.oss.digiwf.process.api.config.api.dto.ProcessConfigTO;
import lombok.RequiredArgsConstructor;


/**
 * ProcessConfigApiImpl is the api to obtain the process configuration.
 */
@RequiredArgsConstructor
public class ProcessConfigApiImpl implements ProcessConfigApi {

    private final ProcessConfigClient processConfigClient;

    /**
     * Obtain a process configuration for a given process definition id from digiwf-engine.
     *
     * @param processDefinitionId the process definition id
     * @return the process configuration
     */
    @Override
    public ProcessConfigTO getProcessConfig(final String processDefinitionId) {
        return this.processConfigClient.getProcessConfig(processDefinitionId);
    }

}
