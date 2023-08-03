/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.engine.basis.process.businesskeyerstellen;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import lombok.val;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;

/**
 * This deleagte creats a business key for the given input variables.
 *
 * @author externer.dl.horn
 */
@Component
public class CreateBusinessKeyDelegate implements JavaDelegate {

    public static final VariableFactory<String> BUSINESS_KEY_VARIABLES = stringVariable("digitalwf_business_key_variables");

    @Override
    public void execute(final DelegateExecution execution) throws Exception {

        //input
        val businessKeyVariables = BUSINESS_KEY_VARIABLES.from(execution).getLocal();

        //processing
        val businessKeyValue = Arrays.stream(businessKeyVariables.split(";"))
                .map(execution::getVariable)
                .map(obj -> obj != null ? obj : "")
                .map(Object::toString)
                .collect(Collectors.joining("/"))
                .toUpperCase();

        //output
        execution.setProcessBusinessKey(businessKeyValue);
    }
}
