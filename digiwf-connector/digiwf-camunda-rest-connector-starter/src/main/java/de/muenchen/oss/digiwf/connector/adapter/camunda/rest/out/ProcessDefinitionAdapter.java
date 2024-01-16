package de.muenchen.oss.digiwf.connector.adapter.camunda.rest.out;

import de.muenchen.oss.digiwf.connector.core.application.port.out.ProcessOutPort;
import org.springframework.stereotype.Service;

@Service
public class ProcessDefinitionAdapter implements ProcessOutPort {

    @Override
    public String loadProcessDefinition(String processInstanceId) {
        // TODO implement me
        return "ProcessDefinition";
    }
}
