package de.muenchen.oss.digiwf.connector.core.application.port.out;

import de.muenchen.oss.digiwf.connector.core.domain.ProcessDefinitionLoadingException;

public interface ProcessOutPort {

    String loadProcessDefinition(final String processInstanceId) throws ProcessDefinitionLoadingException;

}
