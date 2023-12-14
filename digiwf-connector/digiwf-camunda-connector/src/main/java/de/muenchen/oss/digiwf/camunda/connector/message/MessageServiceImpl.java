package de.muenchen.oss.digiwf.camunda.connector.message;


import de.muenchen.oss.digiwf.camunda.connector.data.EngineDataSerializer;
import de.muenchen.oss.digiwf.connector.api.message.CorrelateMessage;
import de.muenchen.oss.digiwf.connector.api.message.MessageService;
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
public class MessageServiceImpl implements MessageService {

    private final MessageApi messageApi;
    private final EngineDataSerializer serializer;

    @Override
    public void correlateMessage(final CorrelateMessage correlateMessage) {
        log.debug("correlateMessage {}", correlateMessage);

        final CorrelationMessageDto correlationMessageDto = new CorrelationMessageDto();
        correlationMessageDto.setMessageName(correlateMessage.getMessageName());

        if (correlateMessage.getPayloadVariables() != null && !correlateMessage.getPayloadVariables().isEmpty()) {
            final Map<String, VariableValueDto> variables = this.serializer.toEngineData(correlateMessage.getPayloadVariables());
            correlationMessageDto.setProcessVariables(variables);
        }

        if (correlateMessage.getPayloadVariablesLocal() != null && !correlateMessage.getPayloadVariablesLocal().isEmpty()) {
            final Map<String, VariableValueDto> variables = this.serializer.toEngineData(correlateMessage.getPayloadVariablesLocal());
            correlationMessageDto.setProcessVariablesLocal(variables);
        }

        if (StringUtils.isNotBlank(correlateMessage.getProcessInstanceId())) {
            correlationMessageDto.setProcessInstanceId(correlateMessage.getProcessInstanceId());
        }

        if (StringUtils.isNotBlank(correlateMessage.getBusinessKey())) {
            correlationMessageDto.setBusinessKey(correlateMessage.getBusinessKey());
        }

        this.messageApi.deliverMessage(correlationMessageDto);
    }

}
