package de.muenchen.oss.digiwf.connector.adapter.camunda.rest.out;

import de.muenchen.oss.digiwf.connector.core.application.port.out.ProcessOutPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessDefinitionAdapter implements ProcessOutPort {

    private final ProcessInstanceClient processInstanceClient;

    @Override
    public String loadProcessDefinition(final String processInstanceId) {
        return processInstanceClient.getRootProcessInstanceDetail(processInstanceId).getBody().getDefinitionName();
    }
}
