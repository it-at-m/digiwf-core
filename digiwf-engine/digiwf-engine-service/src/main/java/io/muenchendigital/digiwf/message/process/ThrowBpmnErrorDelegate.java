package io.muenchendigital.digiwf.message.process;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;

/**
 * Delegate to throw a BPMN error with a dynamic error code.
 * Dynamic error codes (e.g. through expressions) are not supported on an error event.
 */
@Component
@Slf4j
public class ThrowBpmnErrorDelegate implements JavaDelegate {

    private static final String ERROR_CODE_UNKNOWN = "unknown";

    public static final VariableFactory<String> ERROR_CODE = stringVariable("errorCode");
    public static final VariableFactory<String> ERROR_MESSAGE = stringVariable("errorMessage");

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        log.info("Throwing bpmn error for instance {}: {} - {}",
                delegateExecution.getProcessInstanceId(),
                ERROR_CODE.from(delegateExecution).get(),
                ERROR_MESSAGE.from(delegateExecution).get());
        throw new BpmnError(ERROR_CODE.from(delegateExecution).getOrDefault(ERROR_CODE_UNKNOWN));
    }
}
