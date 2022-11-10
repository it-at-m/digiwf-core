package io.muenchendigital.digiwf.cleanup.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.DecisionDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service to update ttl of process definitions, decision tables etc.
 *
 * @author martin.dietrich
 */
@Service
@AllArgsConstructor
@Slf4j
public class TimeToLiveUpdateService {

    private static final int PAGE_SIZE = 100;
    private final RepositoryService repositoryService;

    public void updateResourcesHistoryTimeToLive(Integer timeToLive) {
        updateProcessDefinitionHistoryTimeToLive(timeToLive);
        updateDecisionTableHistoryTimeToLive(timeToLive);
    }

    private void updateProcessDefinitionHistoryTimeToLive(Integer timeToLive) {
        int offset=0, updateCount=0;
        while(true) {
            List<ProcessDefinition> procDefs = queryProcessDefinitions(offset, PAGE_SIZE);
            for (ProcessDefinition procDef : procDefs) {
                if (procDef.getHistoryTimeToLive() == null) {
                    repositoryService.updateProcessDefinitionHistoryTimeToLive(procDef.getId(), timeToLive);
                    log.debug("Updated TTL of process definition {}", procDef.getName());
                    updateCount++;
                }
            }
            offset+=PAGE_SIZE;
            if (procDefs.size()<PAGE_SIZE){
                break;
            }
        }
        log.info("Updated TTL of {} process definitions", updateCount);
    }

    private List<ProcessDefinition> queryProcessDefinitions(int offset, int maxResults) {
        long start = System.currentTimeMillis();
        List<ProcessDefinition> procDefs = repositoryService.createProcessDefinitionQuery()
                .active()
                .listPage(offset, maxResults);
        log.debug("Found process definitions {} to {} (took {} secs)", offset, offset + procDefs.size(), (System.currentTimeMillis() - start) / 1000);
        return procDefs;
    }

    private void updateDecisionTableHistoryTimeToLive(Integer timeToLive) {
        int offset=0, updateCount=0;
        while(true) {
            List<DecisionDefinition> decisionDefs = queryDecisionTables(offset, PAGE_SIZE);
            for (DecisionDefinition decisionDefinition : decisionDefs) {
                if (decisionDefinition.getHistoryTimeToLive() == null) {
                    repositoryService.updateDecisionDefinitionHistoryTimeToLive(decisionDefinition.getId(), timeToLive);
                    log.debug("Updated TTL of decision definition {}", decisionDefinition.getName());
                    updateCount++;
                }
            }
            offset+=PAGE_SIZE;
            if (decisionDefs.size()<PAGE_SIZE){
                break;
            }
        }
        log.info("Updated TTL of {} decision definitions", updateCount);
    }

    private List<DecisionDefinition> queryDecisionTables(int offset, int maxResults) {
        long start = System.currentTimeMillis();
        List<DecisionDefinition> decisionDefs = repositoryService.createDecisionDefinitionQuery()
                .listPage(offset, maxResults);
        log.debug("Found decision definitions {} to {} (took {} secs)", offset, offset + decisionDefs.size(), (System.currentTimeMillis() - start) / 1000);
        return decisionDefs;
    }


}
