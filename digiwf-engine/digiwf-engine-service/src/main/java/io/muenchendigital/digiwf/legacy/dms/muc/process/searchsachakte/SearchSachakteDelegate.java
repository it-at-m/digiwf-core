/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.legacy.dms.muc.process.searchsachakte;

import io.muenchendigital.digiwf.legacy.dms.muc.domain.service.DmsService;
import io.muenchendigital.digiwf.legacy.dms.muc.external.transport.DMSException;
import io.muenchendigital.digiwf.legacy.dms.muc.external.transport.DMSStatusCode;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lombok.val;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;

/**
 * This Delegate get's called to check whether or not the Sachakte exists and returns its COO.
 *
 * @author externer.dl.horn
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class SearchSachakteDelegate implements JavaDelegate {

    private final DmsService dmsService;

    public static final VariableFactory<String> UNTERGRUPPE = stringVariable("dms_untergruppe");
    public static final VariableFactory<String> SACHAKTE_NAME = stringVariable("dms_sachakte_name");
    public static final VariableFactory<String> USER_LOGIN = stringVariable("dms_user_login");

    public static final VariableFactory<String> SACHAKTE_COO = stringVariable("dms_sachakte_coo");

    @Override
    public void execute(final DelegateExecution execution) throws Exception {

        //INPUT
        val untergruppe = UNTERGRUPPE.from(execution).getLocal();
        val sachakte = SACHAKTE_NAME.from(execution).getLocal();
        val userlogin = USER_LOGIN.from(execution).getLocal();

        //PROCESSING
        val searchString = untergruppe + ".*-" + sachakte + "-*";
        try {
            val sachakteCoo = this.dmsService.searchSachakte(
                    searchString,
                    userlogin
            );

            //OUTPUT
            sachakteCoo.ifPresent(s -> SACHAKTE_COO.on(execution).setLocal(s));
        } catch (final DMSException ex) {
            log.error("Could not search for sachakte: {}", ex.getMessage());
            if (DMSStatusCode.FEHLENDE_BERECHTIGUNG.equals(ex.getStatusCode())) {
                throw new BpmnError(ex.getStatusCode().name(), ex.getMessage());
            }
            throw ex;
        }

    }
}
