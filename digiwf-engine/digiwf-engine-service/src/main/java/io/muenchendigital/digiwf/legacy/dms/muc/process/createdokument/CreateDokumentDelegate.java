/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.legacy.dms.muc.process.createdokument;

import io.muenchendigital.digiwf.legacy.dms.muc.domain.model.Dokument;
import io.muenchendigital.digiwf.legacy.dms.muc.domain.model.NeuesSchriftstueck;
import io.muenchendigital.digiwf.legacy.dms.muc.domain.model.Schriftstueck;
import io.muenchendigital.digiwf.legacy.dms.muc.domain.service.DmsService;
import io.muenchendigital.digiwf.legacy.document.domain.DocumentService;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;

/**
 * This Delegate get's called to create a document.
 *
 * @author externer.dl.horn
 */
@Component
@RequiredArgsConstructor
public class CreateDokumentDelegate implements JavaDelegate {

    private final DmsService dmsService;
    private final DocumentService documentService;

    public static final VariableFactory<String> PROZESS_ID = stringVariable("dms_prozess_id");
    public static final VariableFactory<String> COSYS_GUID = stringVariable("dms_cosys_guid");
    public static final VariableFactory<String> VORGANG_COO = stringVariable("dms_vorgang_coo");
    public static final VariableFactory<String> SCHRIFTSTUECK_NAME = stringVariable("dms_schriftstueck_name");
    public static final VariableFactory<String> USER_LOGIN = stringVariable("dms_user_login");
    public static final VariableFactory<String> SCHRIFTSTUECK = stringVariable("dms_created_schriftstueck");

    @Override
    public void execute(final DelegateExecution execution) throws Exception {

        //INPUT
        final String prozessId = PROZESS_ID.from(execution).getLocal();
        final String cosysGuid = COSYS_GUID.from(execution).getLocal();
        final String vorgangCOO = VORGANG_COO.from(execution).getLocal();
        final String schriftstueckName = SCHRIFTSTUECK_NAME.from(execution).getLocal();
        final String userLogin = USER_LOGIN.from(execution).getLocal();

        //PROCESSING
        final byte[] documentContent = this.documentService.createDocument(cosysGuid, prozessId);

        final NeuesSchriftstueck schriftstueck = NeuesSchriftstueck.builder()
                .content(documentContent)
                .extension("pdf")
                .name(schriftstueckName)
                .build();

        final Dokument document = this.dmsService.createDokument(
                schriftstueckName,
                vorgangCOO,
                userLogin,
                Collections.singletonList(schriftstueck));

        final Schriftstueck savedSchriftstueck = document.getSchriftstuecke().get(0);
        SCHRIFTSTUECK.on(execution).setLocal(savedSchriftstueck.getCoo());
    }
}
