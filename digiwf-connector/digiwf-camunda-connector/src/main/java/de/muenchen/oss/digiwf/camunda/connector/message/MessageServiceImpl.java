package de.muenchen.oss.digiwf.camunda.connector.message;


import de.muenchen.oss.digiwf.connector.api.message.CorrelateMessage;
import de.muenchen.oss.digiwf.connector.api.message.MessageService;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.MessageCorrelationBuilder;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Map;

@Slf4j
public class MessageServiceImpl implements MessageService {

    private final RuntimeService runtimeService;

    public MessageServiceImpl(@Qualifier("remote") final RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @Override
    public void correlateMessage(final CorrelateMessage correlateMessage) {
        log.debug("correlateMessage {}", correlateMessage);

        MessageCorrelationBuilder messageCorrelationBuilder = runtimeService.createMessageCorrelation(correlateMessage.getMessageName());

        if (StringUtils.isNotBlank(correlateMessage.getProcessInstanceId())) {
            messageCorrelationBuilder = messageCorrelationBuilder.processInstanceId(correlateMessage.getProcessInstanceId());
        }

        if (StringUtils.isNotBlank(correlateMessage.getBusinessKey())) {
            messageCorrelationBuilder = messageCorrelationBuilder.processInstanceBusinessKey(correlateMessage.getBusinessKey());
        }

        messageCorrelationBuilder
                .setVariables(correlateMessage.getPayloadVariables() != null ? correlateMessage.getPayloadVariables() : Map.of())
                .setVariablesLocal(correlateMessage.getPayloadVariablesLocal() != null ? correlateMessage.getPayloadVariablesLocal() : Map.of())
                .correlate();
    }

}
