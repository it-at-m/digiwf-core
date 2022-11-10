/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.legacy.dms.muc.process.saveschriftstuecke;

import io.muenchendigital.digiwf.legacy.dms.muc.domain.model.Dokument;
import io.muenchendigital.digiwf.legacy.dms.muc.domain.model.Metadata;
import io.muenchendigital.digiwf.legacy.dms.muc.domain.model.NeuesSchriftstueck;
import io.muenchendigital.digiwf.legacy.dms.muc.domain.model.Schriftstueck;
import io.muenchendigital.digiwf.legacy.dms.muc.domain.service.DmsService;
import io.muenchendigital.digiwf.legacy.dms.muc.external.transport.DMSException;
import io.muenchendigital.digiwf.legacy.dms.muc.external.transport.DMSStatusCode;
import io.muenchendigital.digiwf.legacy.dms.shared.BaseSchriftstueck;
import io.muenchendigital.digiwf.legacy.dms.shared.BaseSchriftstueckeData;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lombok.val;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.holunda.camunda.bpm.data.CamundaBpmData.customVariable;
import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;

/**
 * This Delegate get's called to check whether or not the Sachakte exists and returns its COO.
 *
 * @author externer.dl.horn
 */
@Deprecated
@Slf4j
@Component
@RequiredArgsConstructor
public class SaveSchriftstueckeDelegate implements JavaDelegate {

    private final DmsService dmsService;

    public static final VariableFactory<String> USER_LOGIN = stringVariable("dms_user_login");
    public static final VariableFactory<String> DOCUMENT_NAME = stringVariable("dms_document_name");
    public static final VariableFactory<String> VORGANG_COO = stringVariable("dms_vorgang_coo");
    public static final VariableFactory<BaseSchriftstueckeData[]> SCHRIFTSTUECKE = customVariable("dms_read_schriftstuecke_result",
            BaseSchriftstueckeData[].class);

    public static final VariableFactory<String> DOCUMENT_COO = stringVariable("dms_document_coo");
    public static final VariableFactory<SaveSchriftstueckeResult[]> SAVE_SCHRIFTSTUECKE_RESULT = customVariable("dms_save_schriftstuecke_result",
            SaveSchriftstueckeResult[].class);

    @Override
    public void execute(final DelegateExecution execution) throws Exception {

        //INPUT
        final BaseSchriftstueckeData[] data = SCHRIFTSTUECKE.from(execution).get();
        val user = USER_LOGIN.from(execution).getLocal();
        val documentName = DOCUMENT_NAME.from(execution).getLocal();
        val vorgangCOO = VORGANG_COO.from(execution).getLocal();

        //PROCESSING
        val resultData = new ArrayList<SaveSchriftstueckeResult>();

        try {
            val alleSchriftstuecke = Arrays.stream(data)
                    .map(BaseSchriftstueckeData::getSchriftstuecke)
                    .flatMap(Stream::of)
                    .map(this::parse)
                    .collect(Collectors.toList());

            final Dokument document = this.dmsService.createDokument(
                    documentName,
                    vorgangCOO,
                    user,
                    alleSchriftstuecke
            );

            //create result
            int counter = 0;
            for (val schriftstueckeData : data) {

                val result = SaveSchriftstueckeResult.builder()
                        .fieldKey(schriftstueckeData.getFieldKey())
                        .schriftstuecke(this.map(document, Arrays.asList(schriftstueckeData.getSchriftstuecke()), user, counter))
                        .build();
                counter += result.getSchriftstuecke().size();
                resultData.add(result);
            }

            //OUTPUT
            DOCUMENT_COO.on(execution).setLocal(document.getCoo());
            SAVE_SCHRIFTSTUECKE_RESULT.on(execution).setLocal(resultData.toArray(new SaveSchriftstueckeResult[0]));

        } catch (final DMSException exception) {
            if (exception.getStatusCode() == DMSStatusCode.OBJEKT_GESPERRT) {
                throw new BpmnError(exception.getStatusCode().name(), exception.getMessage());
            }
            throw exception;
        } catch (final Exception exception) {
            log.error("An error occurred while saving documents to the target DMS: {}", exception.toString());
            throw exception;
        }
    }

    private NeuesSchriftstueck parse(final BaseSchriftstueck schriftstueck) {
        return NeuesSchriftstueck.builder()
                .content(schriftstueck.getContent())
                .extension(schriftstueck.getExtension())
                .name(schriftstueck.getName())
                .build();
    }

    private List<Metadata> map(final Dokument document, final List<BaseSchriftstueck> baseSchriftstuecke, final String userlogin, final int counter) {
        final List<Schriftstueck> schriftstuecke = new ArrayList<>();
        for (int i = 0; i < baseSchriftstuecke.size(); i++) {
            val doc = document.getSchriftstuecke().get(counter + i);
            schriftstuecke.add(doc);
        }

        return this.dmsService.getMetadata(schriftstuecke, userlogin);
    }

}
