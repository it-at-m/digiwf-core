/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.legacy.dms.muc.process.depositvorgang;

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
 * This Delegate get's called to deposit a Vorgang. Which means that the deadline for keeping the data starts (e.g. 10 years).
 *
 * @author externer.dl.horn
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DepositVorgangDelegate implements JavaDelegate {

    public static final String ERROR_DEPOSIT_FAILED = "AKTENABLAGE_FEHLGESCHLAGEN";
    private final DmsService dmsClient;

    public static final VariableFactory<String> VORGANG_COO = stringVariable("dms_vorgang_coo");
    public static final VariableFactory<String> USER_LOGIN = stringVariable("dms_user_login");

    @Override
    public void execute(final DelegateExecution execution) throws Exception {

        // INPUT
        val vorgangCoo = VORGANG_COO.from(execution).getLocal();
        val userLogin = USER_LOGIN.from(execution).getLocal();

        // PROCESSING
        try {
            this.dmsClient.depositVorgang(vorgangCoo, userLogin);
        } catch (final Exception ex) {
            log.error("Could not deposit vorgang: {}", ex.getMessage());
            if (ex instanceof DMSException) {
                final DMSException dmsException = (DMSException) ex;
                if (DMSStatusCode.OBJEKT_GESPERRT.equals(dmsException.getStatusCode()) ||
                        DMSStatusCode.FEHLENDE_BERECHTIGUNG.equals(dmsException.getStatusCode()) ||
                        DMSStatusCode.UNGUELTIGE_ADRESSE.equals(dmsException.getStatusCode())) {
                    throw new BpmnError(dmsException.getStatusCode().name(), ex.getMessage());
                }
            }
            throw new BpmnError(ERROR_DEPOSIT_FAILED);
        }
    }
}
