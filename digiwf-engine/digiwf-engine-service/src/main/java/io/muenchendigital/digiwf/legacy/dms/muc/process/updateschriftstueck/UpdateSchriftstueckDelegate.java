/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.legacy.dms.muc.process.updateschriftstueck;

import io.muenchendigital.digiwf.legacy.dms.muc.domain.model.Schriftstueck;
import io.muenchendigital.digiwf.legacy.dms.muc.domain.service.DmsService;
import io.muenchendigital.digiwf.legacy.dms.muc.external.transport.DMSException;
import io.muenchendigital.digiwf.legacy.dms.muc.external.transport.DMSStatusCode;
import io.muenchendigital.digiwf.legacy.document.domain.DocumentService;
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
 * This Delegate get's called to update a document in DMS.
 *
 * @author externer.dl.horn
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class UpdateSchriftstueckDelegate implements JavaDelegate {

    private final DmsService dmsService;
    private final DocumentService documentService;

    public static final VariableFactory<String> USER_LOGIN = stringVariable("dms_user_login");
    public static final VariableFactory<String> PROZESS_ID = stringVariable("dms_prozess_id");
    public static final VariableFactory<String> COSYS_GUID = stringVariable("dms_cosys_guid");
    public static final VariableFactory<String> SCHRIFTSTUECK_COO = stringVariable("dms_schriftstueck_coo");
    public static final VariableFactory<String> SCHRIFTSTUECK_NAME = stringVariable("dms_schriftstueck_name");

    @Override
    public void execute(final DelegateExecution execution) throws Exception {

        //INPUT
        val prozessId = PROZESS_ID.from(execution).getLocal();
        val cosysGuid = COSYS_GUID.from(execution).getLocal();
        val schriftstueckCOO = SCHRIFTSTUECK_COO.from(execution).getLocal();
        val schriftstueckName = SCHRIFTSTUECK_NAME.from(execution).getLocal();
        val userLogin = USER_LOGIN.from(execution).getLocal();

        //PROCESSING
        val documentContent = this.documentService.createDocument(cosysGuid, prozessId);

        try {
            val schriftstueck = Schriftstueck.builder()
                    .coo(schriftstueckCOO)
                    .content(documentContent)
                    .extension("pdf")
                    .name(schriftstueckName)
                    .build();

            this.dmsService.updateSchriftstueck(schriftstueck, userLogin);
        } catch (final DMSException ex) {
            log.error("Could not update documents: {}", ex.getMessage());
            if (DMSStatusCode.OBJEKT_GESPERRT.equals(ex.getStatusCode()) ||
                    DMSStatusCode.FEHLENDE_BERECHTIGUNG.equals(ex.getStatusCode())) {
                throw new BpmnError(ex.getStatusCode().name(), ex.getMessage());
            }
            throw ex;
        }
    }
}
