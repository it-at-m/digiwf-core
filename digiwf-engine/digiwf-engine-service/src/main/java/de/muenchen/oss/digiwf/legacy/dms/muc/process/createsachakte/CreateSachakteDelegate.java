/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.legacy.dms.muc.process.createsachakte;

import de.muenchen.oss.digiwf.legacy.dms.muc.domain.service.DmsService;
import de.muenchen.oss.digiwf.legacy.dms.muc.external.transport.DMSException;
import de.muenchen.oss.digiwf.legacy.dms.muc.external.transport.DMSStatusCode;
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
 * This Delegate get's called to create a Sachakte.
 *
 * @author andreas.held
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class CreateSachakteDelegate implements JavaDelegate {

    private final DmsService dmsService;

    public static final VariableFactory<String> SACHAKTE_NAME = stringVariable("dms_sachakte_name");
    public static final VariableFactory<String> AKTENPLAN_NAME = stringVariable("dms_aktenplan_name");
    public static final VariableFactory<String> USER_LOGIN = stringVariable("dms_user_login");

    public static final VariableFactory<String> SACHAKTE_COO = stringVariable("dms_sachakte_coo");

    @Override
    public void execute(final DelegateExecution execution) throws Exception {

        //Input
        val sackakteName = SACHAKTE_NAME.from(execution).getLocal();
        val aktenplanName = AKTENPLAN_NAME.from(execution).getLocal();
        val userLogin = USER_LOGIN.from(execution).getLocal();

        try {

            //PROCESSING
            val akte = this.dmsService.createSachakte(sackakteName, aktenplanName, userLogin);

            //OUTPUT
            SACHAKTE_COO.on(execution).setLocal(akte.getCoo());
        } catch (final DMSException ex) {
            log.error("Could not create sachakte: {}", ex.getMessage());
            if (DMSStatusCode.OBJEKT_GESPERRT.equals(ex.getStatusCode()) ||
                    DMSStatusCode.FEHLENDE_BERECHTIGUNG.equals(ex.getStatusCode()) ||
                    DMSStatusCode.MEHR_ALS_1000_UNTERGEORDNETE_OBJEKTE.equals(ex.getStatusCode())) {
                throw new BpmnError(ex.getStatusCode().name(), ex.getMessage());
            }
            throw ex;
        }
    }
}
