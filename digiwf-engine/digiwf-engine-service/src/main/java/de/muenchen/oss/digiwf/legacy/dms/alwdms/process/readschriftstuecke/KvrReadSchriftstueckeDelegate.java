/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.legacy.dms.alwdms.process.readschriftstuecke;

import com.google.gson.Gson;
import de.muenchen.oss.digiwf.legacy.dms.alwdms.domain.model.AlwSchriftstueck;
import de.muenchen.oss.digiwf.legacy.dms.alwdms.domain.service.AlwDmsService;
import de.muenchen.oss.digiwf.legacy.dms.shared.BaseSchriftstueckeData;
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
 * This Delegate get's called to read schriftstuecke.
 *
 * @author externer.dl.horn
 */
@Deprecated
@Slf4j
@Component
@RequiredArgsConstructor
public class KvrReadSchriftstueckeDelegate implements JavaDelegate {

    private final AlwDmsService dmsService;

    public static final VariableFactory<byte[]> SCHRIFTSTUECKE = customVariable("dms_schriftstuecke", byte[].class);
    public static final VariableFactory<String> USER_LOGIN = stringVariable("dms_user_login");
    public static final VariableFactory<BaseSchriftstueckeData[]> READ_SCHRIFTSTUECKE_RESULT = customVariable("dms_read_schriftstuecke_result",
            BaseSchriftstueckeData[].class);

    @Override
    public void execute(final DelegateExecution execution) throws Exception {

        //INPUT
        val data = new Gson().fromJson(new String(SCHRIFTSTUECKE.from(execution).getLocal()), KvrReadSchriftstueckeData[].class);
        val user = USER_LOGIN.from(execution).getLocal();

        val resultData = new ArrayList<KvrReadSchriftstueckeResult>();

        //PROCESSING
        for (val readData : data) {
            val resultList = new ArrayList<AlwSchriftstueck>();

            for (val schriftstueck : readData.getSchriftstuecke()) {
                try {
                    final AlwSchriftstueck schriftstueckModel = this.dmsService.readSchriftstueck(user, schriftstueck.getUrl());
                    resultList.add(schriftstueckModel);
                } catch (final HttpClientErrorException error) {
                    log.error("Document could not be read from dms kvr: {}", schriftstueck.getUrl(), error);
                    throw error;
                }
            }
            resultData.add(new KvrReadSchriftstueckeResult(readData.getFieldKey(), resultList.toArray(new AlwSchriftstueck[0])));
        }

        //OUTPUT
        READ_SCHRIFTSTUECKE_RESULT.on(execution).set(resultData.toArray(new KvrReadSchriftstueckeResult[0]), true);
    }

}
