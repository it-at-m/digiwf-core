package de.muenchen.oss.digiwf.connector.adapter.camunda.rest.out.processdefinition;

import de.muenchen.oss.digiwf.connector.core.application.port.out.ProcessOutPort;
import de.muenchen.oss.digiwf.connector.core.domain.ProcessDefinitionLoadingException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessDefinitionAdapter implements ProcessOutPort {

    private final ProcessInstanceClient processInstanceClient;

    @Override
    public String loadProcessDefinition(final String processInstanceId) throws ProcessDefinitionLoadingException {
        try {
            return processInstanceClient.getRootProcessInstanceDetail(processInstanceId).getDefinitionName();
        } catch (final FeignException e) {
            throw new ProcessDefinitionLoadingException("Could not load process definition for process instance with id " + processInstanceId, e.getMessage());
        }
    }
}
