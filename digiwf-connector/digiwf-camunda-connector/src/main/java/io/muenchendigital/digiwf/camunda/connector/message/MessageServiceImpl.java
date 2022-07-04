package io.muenchendigital.digiwf.camunda.connector.message;


import io.muenchendigital.digiwf.camunda.connector.data.EngineDataSerializer;
import io.muenchendigital.digiwf.connector.message.api.CorrelateMessage;
import io.muenchendigital.digiwf.connector.message.api.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.community.rest.client.api.MessageApi;
import org.camunda.community.rest.client.dto.CorrelationMessageDto;
import org.camunda.community.rest.client.dto.VariableValueDto;
import org.camunda.community.rest.client.invoker.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@Transactional
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

        try {
            this.messageApi.deliverMessage(correlationMessageDto);
        } catch (final ApiException apiException) {
            log.error("Message could not be sent.", apiException);
            throw new RuntimeException(apiException);
        }
    }

}
