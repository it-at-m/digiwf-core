package de.muenchen.oss.digiwf.connector.adapter.camunda.rest.out;


import de.muenchen.oss.digiwf.connector.adapter.camunda.rest.mapper.EngineDataSerializer;
import de.muenchen.oss.digiwf.connector.core.application.port.out.CorrelateMessageOutPort;
import de.muenchen.oss.digiwf.connector.core.domain.MessageCorrelation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.community.rest.client.api.MessageApi;
import org.camunda.community.rest.client.model.CorrelationMessageDto;
import org.camunda.community.rest.client.model.VariableValueDto;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageAdapter implements CorrelateMessageOutPort {

    private final MessageApi messageApi;
    private final EngineDataSerializer serializer;

    @Override
    public void correlateMessage(final MessageCorrelation messageCorrelation) {
        log.debug("messageCorrelation {}", messageCorrelation);

        final CorrelationMessageDto correlationMessageDto = new CorrelationMessageDto();
        correlationMessageDto.setMessageName(messageCorrelation.getMessageName());

        if (messageCorrelation.getPayloadVariables() != null && !messageCorrelation.getPayloadVariables().isEmpty()) {
            final Map<String, VariableValueDto> variables = this.serializer.toEngineData(messageCorrelation.getPayloadVariables());
            correlationMessageDto.setProcessVariables(variables);
        }

        if (messageCorrelation.getPayloadVariablesLocal() != null && !messageCorrelation.getPayloadVariablesLocal().isEmpty()) {
            final Map<String, VariableValueDto> variables = this.serializer.toEngineData(messageCorrelation.getPayloadVariablesLocal());
            correlationMessageDto.setProcessVariablesLocal(variables);
        }

        if (StringUtils.isNotBlank(messageCorrelation.getProcessInstanceId())) {
            correlationMessageDto.setProcessInstanceId(messageCorrelation.getProcessInstanceId());
        }

        if (StringUtils.isNotBlank(messageCorrelation.getBusinessKey())) {
            correlationMessageDto.setBusinessKey(messageCorrelation.getBusinessKey());
        }
        
        this.messageApi.deliverMessage(correlationMessageDto);
    }

}
