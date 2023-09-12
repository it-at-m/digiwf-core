package de.muenchen.oss.digiwf.process.api.config.api;

import de.muenchen.oss.digiwf.process.api.config.api.dto.ProcessConfigTO;

public interface ProcessConfigApi {

    ProcessConfigTO getProcessConfig(final String processDefinitionId);

}
