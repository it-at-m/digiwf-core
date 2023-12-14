package de.muenchen.oss.digiwf.camunda.connector.bpmnerror;


import de.muenchen.oss.digiwf.camunda.connector.data.EngineDataSerializer;
import de.muenchen.oss.digiwf.connector.api.bpmnerror.BpmnError;
import de.muenchen.oss.digiwf.connector.api.bpmnerror.BpmnErrorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.community.rest.client.api.MessageApi;
import org.camunda.community.rest.client.model.CorrelationMessageDto;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BpmnErrorServiceImpl implements BpmnErrorService {

    public static final String VARIABLEKEY_ERROR_CODE = "errorCode";
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
            correlationMessageDto.putProcessVariablesItem(VARIABLEKEY_ERROR_CODE, this.serializer.toEngineData(bpmnError.getErrorCode()));
        }

        if (StringUtils.isNotBlank(bpmnError.getErrorMessage())) {
            correlationMessageDto.putProcessVariablesItem(VARIABLEKEY_ERROR_MESSAGE, this.serializer.toEngineData(bpmnError.getErrorMessage()));
        }

        this.messageApi.deliverMessage(correlationMessageDto);
    }

}
