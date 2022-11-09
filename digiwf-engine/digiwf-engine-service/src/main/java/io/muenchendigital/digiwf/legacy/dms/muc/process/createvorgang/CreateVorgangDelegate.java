/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.legacy.dms.muc.process.createvorgang;

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
 * This Delegate get's called to create a vorgang.
 *
 * @author externer.dl.horn
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class CreateVorgangDelegate implements JavaDelegate {

    private final DmsService dmsService;

    public static final VariableFactory<String> VORGANG_TITLE = stringVariable("dms_vorgang_title");
    public static final VariableFactory<String> SACHATKE_COO = stringVariable("dms_sachakte_coo");
    public static final VariableFactory<String> USER_LOGIN = stringVariable("dms_user_login");

    public static final VariableFactory<String> VORGANG_COO = stringVariable("dms_vorgang_coo");

    @Override
    public void execute(final DelegateExecution execution) throws Exception {

        //INPUT
        val vorgangTitel = VORGANG_TITLE.from(execution).getLocal();
        val sachakteCOO = SACHATKE_COO.from(execution).getLocal();
        val userLogin = USER_LOGIN.from(execution).getLocal();

        try {
            //PROCESSING
            val vorgang = this.dmsService.createVorgang(vorgangTitel, sachakteCOO, userLogin);

            //OUTPUT
            VORGANG_COO.on(execution).setLocal(vorgang.getCoo());
        } catch (final DMSException ex) {
            log.error("Could not create vorgang: {}", ex.getMessage());
            if (DMSStatusCode.OBJEKT_GESPERRT.equals(ex.getStatusCode()) ||
                    DMSStatusCode.FEHLENDE_BERECHTIGUNG.equals(ex.getStatusCode()) ||
                    DMSStatusCode.MEHR_ALS_1000_UNTERGEORDNETE_OBJEKTE.equals(ex.getStatusCode())) {
                throw new BpmnError(ex.getStatusCode().name(), ex.getMessage());
            }
            throw ex;
        }
    }

}
