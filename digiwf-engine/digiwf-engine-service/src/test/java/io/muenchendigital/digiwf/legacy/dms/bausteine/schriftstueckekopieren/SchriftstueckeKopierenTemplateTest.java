/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.dms.bausteine.schriftstueckekopieren;

import io.muenchendigital.digiwf.legacy.dms.alwdms.domain.model.AlwSchriftstueck;
import io.muenchendigital.digiwf.legacy.dms.alwdms.domain.service.AlwDmsService;
import io.muenchendigital.digiwf.legacy.dms.alwdms.process.KvrDmsFunctions;
import io.muenchendigital.digiwf.legacy.dms.muc.domain.model.Dokument;
import io.muenchendigital.digiwf.legacy.dms.muc.domain.model.EinAusgehend;
import io.muenchendigital.digiwf.legacy.dms.muc.domain.model.Schriftstueck;
import io.muenchendigital.digiwf.legacy.dms.muc.domain.service.DmsService;
import io.muenchendigital.digiwf.legacy.dms.muc.process.mapper.MetadataProcessDataMapper;
import io.muenchendigital.digiwf.legacy.dms.muc.process.saveschriftstuecke.SaveSchriftstueckeEndListener;
import io.muenchendigital.digiwf.legacy.dms.process.CopySchriftstueckeAlwToMucDelegate;

import lombok.val;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.scenario.ProcessScenario;
import org.camunda.bpm.scenario.Scenario;
import org.camunda.bpm.scenario.delegate.TaskDelegate;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.withVariables;
import static org.mockito.Mockito.*;

@Deployment(resources = { "bausteine/dms/schriftstueckekopieren/SchriftstueckeKopierenV01.bpmn",
        "bausteine/dms/schriftstueckekopieren/feature/Feature_SchriftstueckeKopieren.bpmn" })
public class SchriftstueckeKopierenTemplateTest {

    public static final String TEMPLATE_KEY = "FeatureSchriftstueckeKopieren";
    public static final String TASK_SCHRIFTSTUECKE_KOPIEREN = "Task_SchriftstueckeKopieren";
    public static final String TASK_KOPIE_KONTROLLIEREN = "Task_KopieKontrollieren";
    public static final String VAR_FORM_FIELD_QUELL_DMS_USER = "FormField_QuellDmsUser";
    public static final String FORM_FIELD_VORGANG = "FormField_Vorgang";
    public static final String VAR_FORM_FIELD_NACHWEISE_1 = "FormField_Nachweise1";
    public static final String VAR_FORM_FIELD_NACHWEISE_2 = "FormField_Nachweise2";
    public static final String END_EVENT_BEENDET = "EndEvent_Beendet";
    public static final String VAR_STARTER_OF_INSTANCE = "starterOfInstance";
    public static final String END_EVENT = "Event_1t51ccq";

    @Rule
    public ProcessEngineRule rule = new ProcessEngineRule();

    @Mock
    private ProcessScenario processScenario;

    @Mock
    private ProcessScenario templateScenario;

    @Mock
    private AlwDmsService kvrDmsService;

    @Mock
    private DmsService dmsService;

    @Before
    public void defaultScenario() throws Exception {
        MockitoAnnotations.initMocks(this);

        Mocks.register("copySchriftstueckeAlwToMucDelegate", new CopySchriftstueckeAlwToMucDelegate(this.kvrDmsService, this.dmsService));
        Mocks.register("saveSchriftstueckeEndListener", new SaveSchriftstueckeEndListener(new MetadataProcessDataMapper()));
        Mocks.register("kvrDms", new KvrDmsFunctions());

        when(this.kvrDmsService.readSchriftstueck(any(), any())).thenReturn(
                AlwSchriftstueck.builder()
                        .extension("myExtension")
                        .name("myDocument")
                        .build()
        );

        val dokument = Dokument.builder()
                .betreff("Mein Dokument")
                .einAusgehend(EinAusgehend.EINGEHEND)
                .coo("COO")
                .schriftstuecke(this.dmsSchrifststuecke())
                .vorgangId("VorgangCOO")
                .kurzname("MeinDoc")
                .build();

        when(this.dmsService.createDokument(any(), any(), any())).thenReturn(dokument);
        when(this.dmsService.getMetadata(anyString(), anyString())).thenReturn(null);
        when(this.dmsService.getAllSchrifstuecke(any(), any())).thenReturn(dokument.getSchriftstuecke());

        when(this.processScenario.runsCallActivity(TASK_SCHRIFTSTUECKE_KOPIEREN))
                .thenReturn(Scenario.use(this.templateScenario));

        when(this.processScenario.waitsAtUserTask(TASK_KOPIE_KONTROLLIEREN))
                .thenReturn(TaskDelegate::complete);
    }

    @Test
    public void shouldExecuteHappyPath() {
        Scenario.run(this.processScenario)
                .startByKey(TEMPLATE_KEY, withVariables(
                        VAR_FORM_FIELD_QUELL_DMS_USER, "260",
                        FORM_FIELD_VORGANG, "VorgangCOO",
                        VAR_STARTER_OF_INSTANCE, "260",
                        VAR_FORM_FIELD_NACHWEISE_1, this.schriftstuecke(),
                        VAR_FORM_FIELD_NACHWEISE_2, this.schriftstuecke()
                ))
                .execute();

        verify(this.processScenario).hasCompleted(TASK_KOPIE_KONTROLLIEREN);
        verify(this.templateScenario).hasFinished(END_EVENT);
    }

    private List<LinkedHashMap<String, String>> schriftstuecke() {
        val list = new ArrayList<LinkedHashMap<String, String>>();
        val schriftstueck = new LinkedHashMap<String, String>();
        schriftstueck.put("type", "PDF");
        schriftstueck.put("name", "Mein Name");
        schriftstueck.put("url", "https://alwdmstest02.muenchen.de/fsc/mx/COO");
        list.add(schriftstueck);
        return list;

    }

    private List<Schriftstueck> dmsSchrifststuecke() {
        val schriftstueck1 = Schriftstueck.builder()
                .content("null".getBytes())
                .coo("testcoo")
                .extension("ext")
                .name("name")
                .build();

        return Arrays.asList(schriftstueck1, schriftstueck1);

    }

}
