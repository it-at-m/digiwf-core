/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.service.instance.process.instanceexport;

import io.muenchendigital.digiwf.engine.basis.domain.CsvService;
import io.muenchendigital.digiwf.service.instance.domain.service.ServiceInstanceService;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.holunda.camunda.bpm.data.CamundaBpmData.customVariable;
import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;

/**
 * Export process instances to csv.
 *
 * @author externer.dl.horn
 */
@Component
@RequiredArgsConstructor
public class InstanceExportDelegate implements JavaDelegate {

    public static final VariableFactory<String> PROCESS_KEY = stringVariable("digitalwf_process_key");
    public static final VariableFactory<String> VARIABLES = stringVariable("digitalwf_variables");
    public static final VariableFactory<String> STATUS_KEY = stringVariable("digitalwf_status_key");

    public static final VariableFactory<byte[]> PROCESS_EXPORT = customVariable("digitalwf_instance_export", byte[].class);

    private final ServiceInstanceService processInstanceService;
    private final CsvService csvService;

    @Override
    public void execute(final DelegateExecution execution) throws Exception {

        //INPUT
        val processKey = PROCESS_KEY.from(execution).getLocal();
        val variables = VARIABLES.from(execution).getLocal().split(";");
        val statusKey = STATUS_KEY.from(execution).getLocalOptional();

        //PROCESSING

        //1. get all instances
        final List<Map<String, Object>> data;

        if (statusKey.isPresent()) {
            data = this.processInstanceService.getInstanceDataByProcessKey(processKey, Arrays.asList(variables), statusKey.get());
        } else {
            data = this.processInstanceService.getInstanceDataByProcessKey(processKey, Arrays.asList(variables));
        }

        val csv = this.csvService.generateCsv(Arrays.asList(variables), data);

        //  PROCESS_EXPORT
        PROCESS_EXPORT.on(execution).setLocal(csv.getBytes());
    }

}
