package de.muenchen.oss.digiwf.camunda.connector.bpmnerror;


import de.muenchen.oss.digiwf.connector.api.bpmnerror.BpmnError;
import de.muenchen.oss.digiwf.connector.api.bpmnerror.BpmnErrorService;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.MessageCorrelationBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class BpmnErrorServiceImpl implements BpmnErrorService {

    public static final String VARIABLEKEY_ERROR_CODE = "errorCode";
    public static final String VARIABLEKEY_ERROR_MESSAGE = "errorMessage";

    private final RuntimeService runtimeService;

    public BpmnErrorServiceImpl(@Qualifier("remote") final RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @Override
    public void createBpmnError(final BpmnError bpmnError) {
        log.debug("createBpmnError {}", bpmnError);

        MessageCorrelationBuilder messageBuilder = runtimeService.createMessageCorrelation(bpmnError.getMessageName());

        if (StringUtils.isNotBlank(bpmnError.getProcessInstanceId())) {
            messageBuilder = messageBuilder.processInstanceId(bpmnError.getProcessInstanceId());
        }

        // process vars
        final Map<String, Object> processVariables = new HashMap<>();
        if (StringUtils.isNotBlank(bpmnError.getErrorCode())) {
            processVariables.put(VARIABLEKEY_ERROR_CODE, bpmnError.getErrorCode());
        }
        if (StringUtils.isNotBlank(bpmnError.getErrorMessage())) {
            processVariables.put(VARIABLEKEY_ERROR_MESSAGE, bpmnError.getErrorMessage());
        }
        messageBuilder
                .setVariables(processVariables)
                .correlate();
    }

}
