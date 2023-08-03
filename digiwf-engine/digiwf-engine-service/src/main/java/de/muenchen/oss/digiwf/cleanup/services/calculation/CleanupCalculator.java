package de.muenchen.oss.digiwf.cleanup.services.calculation;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Calculates the removal time for a process instance by using the configured strategy.
 *
 * @author martin.dietrich
 */
@Component
@RequiredArgsConstructor
public class CleanupCalculator {

    @Value("${camunda.bpm.generic-properties.properties.history-time-to-live:185}")
    private Integer historyTimeToLive;

    private final RepositoryService repositoryService;

    private final CleanupCalculationStrategy cleanupCalculationStrategy;

    public Date calculateRemovalTime(String processDefinitionKey, Date startTime, Date endTime){
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey)
                .latestVersion()
                .singleResult();
        Integer ttl;
        if (processDefinition.getHistoryTimeToLive() != null){
            ttl = processDefinition.getHistoryTimeToLive();
        }
        else {
            ttl = historyTimeToLive;
        }
        return cleanupCalculationStrategy.calculateRemovalTime(ttl, startTime, endTime);
    }

    public boolean canCalculate(Date startTime, Date endTime){
        return cleanupCalculationStrategy.canCalculate(startTime, endTime);
    }
}
