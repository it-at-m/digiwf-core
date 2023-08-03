package de.muenchen.oss.digiwf.message.domain.service;


import de.muenchen.oss.digiwf.message.domain.model.CorrelateMessage;
import de.muenchen.oss.digiwf.engine.mapper.EngineDataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.MessageCorrelationBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {

    private final RuntimeService runtimeService;
    private final EngineDataMapper engineDataMapper;

    public void correlateMessage(final CorrelateMessage correlateMessage) {
        log.debug("correlateMessage {}", correlateMessage);

        //TODO configure a schema to check if the payload is valid

        final MessageCorrelationBuilder builder = this.runtimeService.createMessageCorrelation(correlateMessage.getMessageName());

        if (correlateMessage.getPayloadVariables() != null && !correlateMessage.getPayloadVariables().isEmpty()) {
            final Map<String, Object> variables = this.engineDataMapper.mapObjectsToVariables(correlateMessage.getPayloadVariables());
            builder.setVariables(variables);
        }

        if (correlateMessage.getPayloadVariablesLocal() != null && !correlateMessage.getPayloadVariablesLocal().isEmpty()) {
            final Map<String, Object> variables = this.engineDataMapper.mapObjectsToVariables(correlateMessage.getPayloadVariablesLocal());
            builder.setVariablesLocal(variables);
        }

        if (StringUtils.isNotBlank(correlateMessage.getProcessInstanceId())) {
            builder.processInstanceId(correlateMessage.getProcessInstanceId());
        }

        if (StringUtils.isNotBlank(correlateMessage.getBusinessKey())) {
            builder.processInstanceBusinessKey(correlateMessage.getBusinessKey());
        }

        builder.correlate();
    }

    public void sendMessage(final String instanceId, final String message, final String userId, final List<String> groups) {
        log.debug("sendMessage {} to instance {}", message, instanceId);

        this.runtimeService.createMessageCorrelation(message)
                .processInstanceId(instanceId)
                .correlate();
    }

}
