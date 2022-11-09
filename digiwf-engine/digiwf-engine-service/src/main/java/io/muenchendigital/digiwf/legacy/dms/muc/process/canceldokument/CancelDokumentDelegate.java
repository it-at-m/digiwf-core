/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.legacy.dms.muc.process.canceldokument;

import io.muenchendigital.digiwf.legacy.dms.muc.domain.service.DmsService;
import io.muenchendigital.digiwf.legacy.dms.muc.external.transport.DMSException;
import io.muenchendigital.digiwf.legacy.dms.muc.external.transport.DMSStatusCode;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;

/**
 * This Delegate get's called to cancel a Document.
 *
 * @author externer.dl.horn
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class CancelDokumentDelegate implements JavaDelegate {

    public static final String ERROR_CANCELATION_FAILED = "LOESCHUNG_FEHLGESCHLAGEN";

    private final DmsService dmsService;

    public static final VariableFactory<String> DOCUMENT_COO = stringVariable("dms_document_coo");
    public static final VariableFactory<String> DMS_USER = stringVariable("dms_user_login");

    @Override
    public void execute(final DelegateExecution execution) throws Exception {

        //INPUT
        val documentCoo = DOCUMENT_COO.from(execution).getLocal();
        val userLogin = DMS_USER.from(execution).getLocal();

        //PROCESSING
        log.debug(String.format("Cancel Document %s with user %s", documentCoo, userLogin));

        if (StringUtils.isBlank(documentCoo)) {
            return;
        }

        try {
            this.dmsService.cancelDocument(userLogin, documentCoo);
        } catch (final Exception error) {
            log.error("Could not cancel documents: {}", error.getMessage());
            // check if object is locked if it is a DMSException
            if (error instanceof DMSException) {
                final DMSException dmsException = (DMSException) error;
                if (dmsException.getStatusCode() == DMSStatusCode.OBJEKT_GESPERRT) {
                    throw new BpmnError(dmsException.getStatusCode().name(), dmsException.getMessage());
                }
            }
            throw new BpmnError(ERROR_CANCELATION_FAILED);
        }

    }
}
