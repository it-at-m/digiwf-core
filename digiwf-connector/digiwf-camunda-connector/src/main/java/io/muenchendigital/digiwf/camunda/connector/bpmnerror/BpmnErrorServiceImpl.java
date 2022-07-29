package io.muenchendigital.digiwf.camunda.connector.bpmnerror;


import io.muenchendigital.digiwf.camunda.connector.data.EngineDataSerializer;
import io.muenchendigital.digiwf.connector.bpmnerror.api.BpmnError;
import io.muenchendigital.digiwf.connector.bpmnerror.api.BpmnErrorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.community.rest.client.api.MessageApi;
import org.camunda.community.rest.client.dto.CorrelationMessageDto;
import org.camunda.community.rest.client.invoker.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BpmnErrorServiceImpl implements BpmnErrorService {

    public static final String VARIABLEKEY_ERROR_CODE    = "errorCode";
    public static final String VARIABLEKEY_ERROR_MESSAGE = "errorMessage";

    private final MessageApi messageApi;
    private final EngineDataSerializer serializer;

    @Override
    public void createBpmnError(final BpmnError bpmnError) {
        log.debug("createBpmnError {}", bpmnError);

        final CorrelationMessageDto correlationMessageDto = new CorrelationMessageDto();
        correlationMessageDto.setMessageName(bpmnError.getMessageName());

        if (StringUtils.isNotBlank(bpmnError.getProcessInstanceId())) {
            correlationMessageDto.setProcessInstanceId(bpmnError.getProcessInstanceId());
        }

        if (StringUtils.isNotBlank(bpmnError.getErrorCode())) {
            correlationMessageDto.putProcessVariablesItem(VARIABLEKEY_ERROR_CODE, serializer.toEngineData(bpmnError.getErrorCode()));
        }

        if (StringUtils.isNotBlank(bpmnError.getErrorMessage())) {
            correlationMessageDto.putProcessVariablesItem(VARIABLEKEY_ERROR_MESSAGE, serializer.toEngineData(bpmnError.getErrorMessage()));
        }

        try {
            this.messageApi.deliverMessage(correlationMessageDto);
        } catch (final ApiException apiException) {
            log.error("Bpmn error could not be sent.", apiException);
            throw new RuntimeException(apiException);
        }
    }

}
