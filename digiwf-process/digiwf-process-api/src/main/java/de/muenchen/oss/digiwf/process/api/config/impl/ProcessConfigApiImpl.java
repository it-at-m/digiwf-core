package de.muenchen.oss.digiwf.process.api.config.impl;

import de.muenchen.oss.digiwf.process.api.config.api.ProcessConfigApi;
import de.muenchen.oss.digiwf.process.api.config.api.dto.ProcessConfigTO;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ProcessConfigApiImpl implements ProcessConfigApi {

    private final ProcessConfigClient processConfigClient;

    @Override
    public ProcessConfigTO getProcessConfig(String processDefinitionId) {
        return this.processConfigClient.getProcessConfig(processDefinitionId);
    }

}
