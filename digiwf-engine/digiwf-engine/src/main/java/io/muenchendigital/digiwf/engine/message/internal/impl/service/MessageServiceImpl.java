package io.muenchendigital.digiwf.engine.message.internal.impl.service;


import io.muenchendigital.digiwf.engine.data.EngineDataSerializer;
import io.muenchendigital.digiwf.engine.message.api.CorrelateMessage;
import io.muenchendigital.digiwf.engine.message.api.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.MessageCorrelationBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final RuntimeService runtimeService;
    private final EngineDataSerializer serializer;

    @Override
    public void correlateMessage(final CorrelateMessage correlateMessage) {
        log.debug("correlateMessage {}", correlateMessage);

        //TODO configure a schema to check if the payload is valid

        final MessageCorrelationBuilder builder = this.runtimeService.createMessageCorrelation(correlateMessage.getMessageName());

        if (correlateMessage.getPayloadVariables() != null && !correlateMessage.getPayloadVariables().isEmpty()) {
            final Map<String, Object> variables = this.serializer.toEngineData(correlateMessage.getPayloadVariables());
            builder.setVariables(variables);
        }

        if (correlateMessage.getPayloadVariablesLocal() != null && !correlateMessage.getPayloadVariablesLocal().isEmpty()) {
            final Map<String, Object> variables = this.serializer.toEngineData(correlateMessage.getPayloadVariablesLocal());
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

}
