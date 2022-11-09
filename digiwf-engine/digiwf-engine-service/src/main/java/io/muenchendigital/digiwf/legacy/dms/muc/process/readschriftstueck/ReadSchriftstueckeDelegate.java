/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.legacy.dms.muc.process.readschriftstueck;

import com.google.gson.Gson;
import io.muenchendigital.digiwf.legacy.dms.muc.domain.model.Schriftstueck;
import io.muenchendigital.digiwf.legacy.dms.muc.domain.service.DmsService;
import io.muenchendigital.digiwf.legacy.dms.shared.BaseSchriftstueckeData;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lombok.val;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;

import static io.holunda.camunda.bpm.data.CamundaBpmData.customVariable;
import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;

/**
 * This Delegate get's called to check whether or not the Sachakte exists and returns its COO.
 *
 * @author externer.dl.horn
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ReadSchriftstueckeDelegate implements JavaDelegate {

    private final DmsService dmsService;

    public static final VariableFactory<String> SCHRIFTSTUECKE = stringVariable("dms_schriftstuecke");
    public static final VariableFactory<String> USER_LOGIN = stringVariable("dms_user_login");
    public static final VariableFactory<BaseSchriftstueckeData[]> READ_SCHRIFTSTUECKE_RESULT = customVariable("dms_read_schriftstuecke_result",
            BaseSchriftstueckeData[].class);

    @Override
    public void execute(final DelegateExecution execution) throws Exception {

        //INPUT
        val data = new Gson().fromJson(SCHRIFTSTUECKE.from(execution).getLocal(), ReadSchriftstueckeData[].class);
        val user = USER_LOGIN.from(execution).getLocal();

        val resultData = new ArrayList<ReadSchriftstueckeResult>();

        //PROCESSING
        for (val readData : data) {
            val resultList = new ArrayList<Schriftstueck>();

            for (val schriftstueck : readData.getSchriftstuecke()) {
                try {
                    val schriftstueckModel = this.dmsService.readSchriftstueck(user, schriftstueck.getUrl());
                    resultList.add(schriftstueckModel);
                } catch (final HttpClientErrorException error) {
                    log.error("Document could not be read from dms kvr: " + schriftstueck.getUrl(), error);
                    throw error;
                }
            }
            resultData.add(new ReadSchriftstueckeResult(readData.getFieldKey(), resultList.toArray(new Schriftstueck[0])));
        }

        //OUTPUT
        READ_SCHRIFTSTUECKE_RESULT.on(execution).set(resultData.toArray(new ReadSchriftstueckeResult[0]), true);
    }

}
