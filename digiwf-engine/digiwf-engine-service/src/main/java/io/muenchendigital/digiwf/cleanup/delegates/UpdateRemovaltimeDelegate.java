package io.muenchendigital.digiwf.cleanup.delegates;

import io.muenchendigital.digiwf.cleanup.services.RemovaltimeUpdateService;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.holunda.camunda.bpm.data.CamundaBpmData.intVariable;
import static io.holunda.camunda.bpm.data.CamundaBpmData.listVariable;

/**
 * Delegate to update removal time of a process instance.
 *
 * @author martin.dietrich
 */
@Component
@AllArgsConstructor
@Slf4j
public class UpdateRemovaltimeDelegate implements JavaDelegate {

    private final RemovaltimeUpdateService removaltimeUpdateService;

    public static final VariableFactory<List<String>> ASSEMBLED_UPDATE_REMOVALTIME_BATCH = listVariable("assembled_update_removaltime_batch", String.class);
    public static final VariableFactory<Integer> UPDATED_PROCESS_INSTANCES = intVariable("updated_process_instances");

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        log.debug("Updating Removaltime");
        final List<String> instanceIds = ASSEMBLED_UPDATE_REMOVALTIME_BATCH.from(delegateExecution).get();
        for (String instanceId : instanceIds) {
            if (removaltimeUpdateService.updateServiceInstance(instanceId)){
                incrementCounter(delegateExecution);
            }
        }
    }

    private void incrementCounter(final DelegateExecution delegateExecution) {
        Integer counter = UPDATED_PROCESS_INSTANCES.from(delegateExecution).get();
        UPDATED_PROCESS_INSTANCES.on(delegateExecution).set(++counter);
        log.debug("Update counter: {}", counter);
    }

}
