package io.muenchendigital.digiwf.cleanup.delegates;

import io.muenchendigital.digiwf.cleanup.services.TimeToLiveUpdateService;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import static io.holunda.camunda.bpm.data.CamundaBpmData.intVariable;

/**
 * Delegate to update all ttls in process definitions, DMSs etc.
 *
 * @author martin.dietrich
 */
@Component
@AllArgsConstructor
@Slf4j
public class UpdateTimeToLiveDelegate implements JavaDelegate {

    private final TimeToLiveUpdateService cleanupService;

    public static final VariableFactory<Integer> TIME_TO_LIVE = intVariable("digiwf_time_to_live");

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("Updating TTL");
        val timeToLive = TIME_TO_LIVE.from(delegateExecution).getLocal();

        cleanupService.updateResourcesHistoryTimeToLive(timeToLive);

    }
}
