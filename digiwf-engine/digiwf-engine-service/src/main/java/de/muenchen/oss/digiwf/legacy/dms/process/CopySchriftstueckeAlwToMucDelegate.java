/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.dms.process;

import com.google.gson.Gson;
import de.muenchen.oss.digiwf.legacy.dms.alwdms.domain.model.AlwMetadata;
import de.muenchen.oss.digiwf.legacy.dms.alwdms.domain.model.AlwSchriftstueck;
import de.muenchen.oss.digiwf.legacy.dms.alwdms.domain.service.AlwDmsService;
import de.muenchen.oss.digiwf.legacy.dms.alwdms.process.readschriftstuecke.KvrReadSchriftstueckeData;
import de.muenchen.oss.digiwf.legacy.dms.alwdms.process.readschriftstuecke.KvrReadSchriftstueckeResult;
import de.muenchen.oss.digiwf.legacy.dms.muc.domain.model.Dokument;
import de.muenchen.oss.digiwf.legacy.dms.muc.domain.model.Metadata;
import de.muenchen.oss.digiwf.legacy.dms.muc.domain.model.NeuesSchriftstueck;
import de.muenchen.oss.digiwf.legacy.dms.muc.domain.model.Schriftstueck;
import de.muenchen.oss.digiwf.legacy.dms.muc.domain.service.DmsService;
import de.muenchen.oss.digiwf.legacy.dms.muc.process.saveschriftstuecke.SaveSchriftstueckeResult;
import de.muenchen.oss.digiwf.legacy.dms.shared.BaseSchriftstueck;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.holunda.camunda.bpm.data.CamundaBpmData.customVariable;
import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;

/**
 * This Delegate get's called to copy schriftstuecke from alw to muc
 *
 * @author externer.dl.horn
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CopySchriftstueckeAlwToMucDelegate implements JavaDelegate {

    private final AlwDmsService alwDmsService;
    private final DmsService dmsService;

    public static final VariableFactory<byte[]> SCHRIFTSTUECKE = customVariable("dms_schriftstuecke", byte[].class);
    public static final VariableFactory<String> USER_LOGIN_ALW = stringVariable("dms_user_login_alw");
    public static final VariableFactory<String> USER_LOGIN_MUC = stringVariable("dms_user_login_muc");
    public static final VariableFactory<String> DOCUMENT_NAME = stringVariable("dms_document_name");
    public static final VariableFactory<String> VORGANG_COO = stringVariable("dms_vorgang_coo");

    public static final VariableFactory<String> DOCUMENT_COO = stringVariable("dms_document_coo");
    public static final VariableFactory<SaveSchriftstueckeResult[]> SAVE_SCHRIFTSTUECKE_RESULT = customVariable("dms_save_schriftstuecke_result",
            SaveSchriftstueckeResult[].class);

    @Override
    public void execute(final DelegateExecution execution) throws Exception {

        //INPUT
        final KvrReadSchriftstueckeData[] data = new Gson().fromJson(new String(SCHRIFTSTUECKE.from(execution).getLocal()), KvrReadSchriftstueckeData[].class);
        final String alwUser = USER_LOGIN_ALW.from(execution).getLocal();
        final String userMuc = USER_LOGIN_MUC.from(execution).getLocal();
        final String documentName = DOCUMENT_NAME.from(execution).getLocal();
        final String vorgangCOO = VORGANG_COO.from(execution).getLocal();

        //PROCESSING

        //read from alw
        final List<KvrReadSchriftstueckeResult> readResult = new ArrayList<>();

        for (val readData : data) {
            final List<AlwSchriftstueck> resultList = new ArrayList<>();

            for (final AlwMetadata schriftstueck : readData.getSchriftstuecke()) {
                try {
                    final AlwSchriftstueck schriftstueckModel = this.alwDmsService.readSchriftstueck(alwUser, schriftstueck.getUrl());
                    resultList.add(schriftstueckModel);
                } catch (final HttpClientErrorException error) {
                    log.error("Document could not be read from dms kvr: {}", schriftstueck.getUrl(), error);
                    throw error;
                }
            }
            readResult.add(KvrReadSchriftstueckeResult.builder()
                    .fieldKey(readData.getFieldKey())
                    .schriftstuecke(resultList.toArray(new AlwSchriftstueck[0]))
                    .build());
        }

        //save to muc dms
        final List<SaveSchriftstueckeResult> resultData = new ArrayList<>();
        final List<NeuesSchriftstueck> alleSchriftstuecke = readResult.stream()
                .map(KvrReadSchriftstueckeResult::getSchriftstuecke)
                .flatMap(Stream::of)
                .map(this::parse)
                .collect(Collectors.toList());

        final Dokument document = this.dmsService.createDokument(
                documentName,
                vorgangCOO,
                userMuc
        );

        for (final NeuesSchriftstueck schriftstueck : alleSchriftstuecke) {
            this.dmsService.updateDocument(
                    document,
                    Collections.singletonList(schriftstueck),
                    userMuc);
        }

        //create result
        final List<Schriftstueck> updatedSchriftstuecke = this.dmsService.getAllSchrifstuecke(document.getCoo(), userMuc);
        int counter = 0;
        for (final KvrReadSchriftstueckeData schriftstueckeData : data) {

            final SaveSchriftstueckeResult result = SaveSchriftstueckeResult.builder()
                    .fieldKey(schriftstueckeData.getFieldKey())
                    .schriftstuecke(this.map(updatedSchriftstuecke, schriftstueckeData.getSchriftstuecke().size(), userMuc, counter))
                    .build();
            counter += result.getSchriftstuecke().size();
            resultData.add(result);
        }

        //OUTPUT
        DOCUMENT_COO.on(execution).setLocal(document.getCoo());
        SAVE_SCHRIFTSTUECKE_RESULT.on(execution).setLocal(resultData.toArray(new SaveSchriftstueckeResult[0]));

    }

    private NeuesSchriftstueck parse(final BaseSchriftstueck schriftstueck) {
        return NeuesSchriftstueck.builder()
                .content(schriftstueck.getContent())
                .extension(schriftstueck.getExtension())
                .name(schriftstueck.getName())
                .build();
    }

    private List<Metadata> map(final List<Schriftstueck> schriftstuecke, final int size, final String userlogin,
                               final int counter) {
        final List<Schriftstueck> fieldDchriftstuecke = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            val doc = schriftstuecke.get(counter + i);
            fieldDchriftstuecke.add(doc);
        }

        return this.dmsService.getMetadata(fieldDchriftstuecke, userlogin);
    }

}
