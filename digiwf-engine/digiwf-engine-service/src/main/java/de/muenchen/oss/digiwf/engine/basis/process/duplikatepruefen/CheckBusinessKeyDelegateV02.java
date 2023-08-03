/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.engine.basis.process.duplikatepruefen;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

import static io.holunda.camunda.bpm.data.CamundaBpmData.booleanVariable;
import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;

/**
 * This delegate checks if there are multiple processes with the same business key.+
 *
 * @author externer.dl.horn
 */
@Component
@RequiredArgsConstructor
public class CheckBusinessKeyDelegateV02 implements JavaDelegate {

    private final RuntimeService runtimeService;
    private final HistoryService historyService;

    public static final VariableFactory<String> EXISTING_PROCESS_INSTANCES = stringVariable("app_existing_process_instances");
    public static final VariableFactory<Boolean> PROCESSES_EXIST = booleanVariable("app_processes_exist");
    public static final VariableFactory<String> PROCESS_DEFINITION_KEY = stringVariable("app_process_definition_key");
    public static final VariableFactory<String> PROCESS_INSTANCE_ID = stringVariable("app_process_instance_id");
    public static final VariableFactory<String> PROCESS_BUSINESS_KEY = stringVariable("app_process_business_key");

    @Override
    public void execute(final DelegateExecution execution) throws Exception {

        //input
        val businessKey = PROCESS_BUSINESS_KEY.from(execution).getLocal();
        val processDefintionKey = PROCESS_DEFINITION_KEY.from(execution).getLocal();
        val processInstanceId = PROCESS_INSTANCE_ID.from(execution).getLocal();

        if (StringUtils.isBlank(businessKey)) {

            //Output
            PROCESSES_EXIST.on(execution).setLocal(false);
            EXISTING_PROCESS_INSTANCES.on(execution).setLocal("");
            return;
        }


        val existingProcessInstances = this.runtimeService.createProcessInstanceQuery()
                .processInstanceBusinessKey(businessKey)
                .processDefinitionKey(processDefintionKey)
                .list()
                .stream()
                .filter(instance -> !instance.getProcessInstanceId().equals(processInstanceId))
                .collect(Collectors.toList());

        if (existingProcessInstances.size() == 0) {

            //OUTPUT
            PROCESSES_EXIST.on(execution).setLocal(false);
            EXISTING_PROCESS_INSTANCES.on(execution).setLocal("");
            return;
        }

        final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        val existingProcessInstancesMessage = existingProcessInstances.stream()
                .map(instance -> this.historyService.createHistoricProcessInstanceQuery().processInstanceId(instance.getProcessInstanceId()).singleResult())
                .map(instance -> "Start Zeit: " + dateFormat.format(instance.getStartTime()))
                .collect(Collectors.joining(", "));

        //OUTPUT
        EXISTING_PROCESS_INSTANCES.on(execution).setLocal(existingProcessInstancesMessage);
        PROCESSES_EXIST.on(execution).setLocal(true);
    }
}
