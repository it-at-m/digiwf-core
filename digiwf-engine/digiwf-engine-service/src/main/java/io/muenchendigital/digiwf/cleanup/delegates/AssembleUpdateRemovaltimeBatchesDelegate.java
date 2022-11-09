package io.muenchendigital.digiwf.cleanup.delegates;

import io.muenchendigital.digiwf.cleanup.services.RemovaltimeUpdateService;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static io.holunda.camunda.bpm.data.CamundaBpmData.intVariable;
import static io.holunda.camunda.bpm.data.CamundaBpmData.listVariable;

/**
 * Delegate to collect process instances to update their removaltime.
 *
 * @author martin.dietrich
 */
@Component
@AllArgsConstructor
@Slf4j
public class AssembleUpdateRemovaltimeBatchesDelegate implements JavaDelegate {

    private final RemovaltimeUpdateService removaltimeUpdateService;

    public static final VariableFactory<List<List>> ASSEMBLED_UPDATE_REMOVALTIME_BATCHES = listVariable("assembled_update_removaltime_batches", List.class);
    public static final VariableFactory<Integer> UPDATED_PROCESS_INSTANCES = intVariable("updated_process_instances");

    private static final int BATCH_SIZE = 200;


    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        log.info("Assemble batches for update removaltime");
        final List<String> instanceIdList = removaltimeUpdateService.listUpdateableServiceInstances();
        final List<List> updateBatches = splitList(instanceIdList);
        log.info("Assembled {} batches (size {})", updateBatches.size(), BATCH_SIZE);
        ASSEMBLED_UPDATE_REMOVALTIME_BATCHES.on(delegateExecution).set(updateBatches);
        UPDATED_PROCESS_INSTANCES.on(delegateExecution).set(0);

    }

    private List<List> splitList(final List<String> list) {
        final AtomicInteger counter = new AtomicInteger();
        final Collection<List<String>> result = list.stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / BATCH_SIZE))
                .values();
        return new ArrayList<>(result);
    }

}
