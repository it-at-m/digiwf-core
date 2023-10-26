/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.dms.bausteine.vorganganlegen;

import de.muenchen.oss.digiwf.engine.basis.process.DigitalWFFunctions;
import de.muenchen.oss.digiwf.legacy.dms.muc.domain.model.Dokument;
import de.muenchen.oss.digiwf.legacy.dms.muc.domain.model.Sachakte;
import de.muenchen.oss.digiwf.legacy.dms.muc.domain.model.Schriftstueck;
import de.muenchen.oss.digiwf.legacy.dms.muc.domain.model.Vorgang;
import de.muenchen.oss.digiwf.legacy.dms.muc.domain.service.DmsService;
import de.muenchen.oss.digiwf.legacy.dms.muc.external.transport.DMSException;
import de.muenchen.oss.digiwf.legacy.dms.muc.external.transport.DMSStatusCode;
import de.muenchen.oss.digiwf.legacy.dms.muc.process.createdokument.CreateDokumentDelegate;
import de.muenchen.oss.digiwf.legacy.dms.muc.process.createsachakte.CreateSachakteDelegate;
import de.muenchen.oss.digiwf.legacy.dms.muc.process.createvorgang.CreateVorgangDelegate;
import de.muenchen.oss.digiwf.legacy.dms.muc.process.searchsachakte.SearchSachakteDelegate;
import de.muenchen.oss.digiwf.legacy.dms.shared.S3Resolver;
import de.muenchen.oss.digiwf.legacy.document.domain.DocumentService;
import de.muenchen.oss.digiwf.legacy.mailing.process.TestSendMailDelegate;
import de.muenchen.oss.digiwf.legacy.user.process.UserFunctions;
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

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.withVariables;
import static org.mockito.Mockito.*;

@Deployment(resources = {"bausteine/dms/vorganganlegen/VorgangAnlegenV02.bpmn", "bausteine/dms/vorganganlegen/VorgangAnlegenV01.bpmn", "bausteine/dms/vorganganlegen/feature/Feature_VorgangAnlegen.bpmn", "bausteine/dms/vorganganlegen/feature/Feature_VorgangAnlegenV02S3.bpmn"})
public class VorgangAnlegenTemplateTest {

    public static final String TEMPLATE_KEY = "FeatureVorgangAnlegen";
    public static final String TEMPLATE_KEY_S3 = "FeatureVorgangAnlegenV02S3";
    public static final String VAR_STARTER_OF_INSTANCE = "starterOfInstance";
    public static final String VAR_FORM_FIELD_VORGANG_TITEL = "FormField_VorgangTitel";
    public static final String VAR_FORM_FIELD_UNTERGRUPPE = "FormField_Untergruppe";
    public static final String VAR_FORM_FIELD_SACHAKTE_NAME = "FormField_SachakteName";
    public static final String VAR_FORM_FIELD_AKTENPLAN_NAME = "FormField_AktenplanName";
    public static final String VAR_S3_URL = "s3_url";
    public static final String TASK_KONTROLLIEREN = "Task_Kontrollieren";
    public static final String END_EVENT_BEENDET = "EndEvent_Beendet";
    public static final String TASK_VORGANG_ANLEGEN = "Task_VorgangAnlegen";
    public static final String VAR_FORM_FIELD_NEUE_SACHBEARBEITUNG = "FormField_Neue_Sachbearbeitung";
    public static final String TASK_ZUSTAENDIGKEIT_AENDERN_ODER_VERGABE_VON_RECHTEN_BEAUFTRAGEN = "Task_ZustaendigkeitAendernOderVergabeVonRechtenBeauftragen";
    public static final String VORGANG_ANLEGEN_V_1_SACHAKTE_ANLEGEN = "VorgangAnlegenV1_SachakteAnlegen";
    public static final String TASK_SPERRE_BETREFFSEINHEIT_AUFHEBEN = "Task_SperreBetreffseinheitAufheben";
    public static final String TASK_BETREFFSEINHEITEN_ANLEGEN = "Task_BetreffseinheitenAnlegen";
    public static final String TASK_SACHAKTE_ZUSTAENDIKEIT_AENDERN_ODER_VERGABE_VON_SCHREIBRECHTEN_BEAUFTRAGEN = "Task_SachakteZustaendikeitAendernOderVergabeVonSchreibrechtenBeauftragen";
    public static final String TASK_SPERRE_AKTE_AUFHEBEN = "Task_SperreAkteAufheben";
    public static final String TASK_VORGANG_ZUSTAENDIGKEIT_AENDERN_ODER_VERGABE_VON_SCHREIBRECHTEN_BEAUFTRAGEN = "Task_VorgangZustaendigkeitAendernOderVergabeVonSchreibrechtenBeauftragen";

    @Rule
    public ProcessEngineRule rule = new ProcessEngineRule();

    @Mock
    private ProcessScenario processScenario;

    @Mock
    private ProcessScenario templateScenario;

    @Mock
    private DmsService dmsService;

    @Mock
    private DocumentService documentService;

    @Mock
    private DigitalWFFunctions digitalWF;

    @Mock
    private S3Resolver s3Resolver;

    @Mock
    private UserFunctions user;

    @Before
    public void defaultScenario() throws Exception {
        MockitoAnnotations.initMocks(this);

        Mocks.register("searchSachakteDelegate", new SearchSachakteDelegate(this.dmsService));
        when(this.dmsService.searchSachakte(any(), any())).thenReturn(Optional.of("sachakteCOO"));

        Mocks.register("createVorgangDelegate", new CreateVorgangDelegate(this.dmsService));
        when(this.dmsService.createVorgang(any(), any(), any())).thenReturn(Vorgang.builder().coo("VorgangCOO").build());

        Mocks.register("createDokumentDelegate", new CreateDokumentDelegate(this.dmsService, this.documentService, this.s3Resolver));
        when(this.documentService.createDocument(anyString(), anyString())).thenReturn("Document".getBytes());
        when(this.dmsService.createDokument(any(), any(), any(), any())).thenReturn(
                Dokument.builder()
                        .schriftstuecke(Collections.singletonList(Schriftstueck.builder().coo("SchriftstueckCOO").build()))
                        .build()
        );

        Mocks.register("digitalwf", this.digitalWF);
        when(this.digitalWF.urlGruppenaufgaben()).thenReturn("myurl");

        Mocks.register("user", this.user);
        when(this.user.firstname(any())).thenReturn("Tester");
        when(this.user.lastname(any())).thenReturn("Test");
        when(this.user.ou(any())).thenReturn("Group");

        Mocks.register("sendMailDelegate", new TestSendMailDelegate());

        Mocks.register("createSachakteDelegate", new CreateSachakteDelegate(this.dmsService));
        when(this.dmsService.createSachakte(any(), any(), any())).thenReturn(Sachakte.builder().coo("SachakteCOO").build());

        //Happy Path
        when(this.processScenario.runsCallActivity(TASK_VORGANG_ANLEGEN))
                .thenReturn(Scenario.use(this.templateScenario));

        when(this.processScenario.waitsAtUserTask(TASK_KONTROLLIEREN))
                .thenReturn(TaskDelegate::complete);

        //Sachakte fehlende Berechtigung
        when(this.templateScenario.waitsAtUserTask(TASK_ZUSTAENDIGKEIT_AENDERN_ODER_VERGABE_VON_RECHTEN_BEAUFTRAGEN))
                .thenReturn(task -> task.complete(withVariables(VAR_FORM_FIELD_NEUE_SACHBEARBEITUNG, "neuerDmsUser")));

        //Sachakte gesperrt
        when(this.templateScenario.waitsAtUserTask(TASK_SPERRE_BETREFFSEINHEIT_AUFHEBEN))
                .thenReturn(TaskDelegate::complete);

        //Betreffseinheit gesperrt
        when(this.templateScenario.waitsAtUserTask(TASK_SPERRE_BETREFFSEINHEIT_AUFHEBEN))
                .thenReturn(TaskDelegate::complete);

        //Betreffseinheit anlegen
        when(this.templateScenario.waitsAtUserTask(TASK_BETREFFSEINHEITEN_ANLEGEN))
                .thenReturn(TaskDelegate::complete);

        //Zuständigkeit ändern
        when(this.templateScenario.waitsAtUserTask(TASK_SACHAKTE_ZUSTAENDIKEIT_AENDERN_ODER_VERGABE_VON_SCHREIBRECHTEN_BEAUFTRAGEN))
                .thenReturn(task -> task.complete(withVariables(VAR_FORM_FIELD_NEUE_SACHBEARBEITUNG, "neuerDmsUser")));

        //Vorgang-Akte aufheben
        when(this.templateScenario.waitsAtUserTask(TASK_SPERRE_AKTE_AUFHEBEN))
                .thenReturn(TaskDelegate::complete);

        //Vorgang anlegen Zuständigkeit ändern
        when(this.templateScenario.waitsAtUserTask(TASK_VORGANG_ZUSTAENDIGKEIT_AENDERN_ODER_VERGABE_VON_SCHREIBRECHTEN_BEAUFTRAGEN))
                .thenReturn(task -> task.complete(withVariables(VAR_FORM_FIELD_NEUE_SACHBEARBEITUNG, "neuerDmsUser")));

    }

    @Test
    public void shouldExecuteHappyPath() {
        Scenario.run(this.processScenario)
                .startByKey(TEMPLATE_KEY, this.getVariableMap())
                .execute();

        verify(this.processScenario).hasCompleted(TASK_KONTROLLIEREN);
        verify(this.processScenario).hasFinished(END_EVENT_BEENDET);
    }

    @Test
    public void shouldExecuteS3HappyPath() throws IOException {
        Scenario.run(this.processScenario)
                .startByKey(TEMPLATE_KEY_S3, this.getS3VariableMap())
                .execute();

        verify(this.s3Resolver).getS3File(any());

        verify(this.processScenario).hasCompleted(TASK_KONTROLLIEREN);
        verify(this.processScenario).hasFinished(END_EVENT_BEENDET);
    }

    @Test
    public void shouldExecuteWithSachakteSuchenFehlendeBerechtigung() throws Exception {
        //Throw error the first time
        when(this.dmsService.searchSachakte(any(), any()))
                .thenThrow(new DMSException(DMSStatusCode.FEHLENDE_BERECHTIGUNG, "gesperrt"))
                .thenReturn(Optional.of("sachakteCOO"));

        Scenario.run(this.processScenario)
                .startByKey(TEMPLATE_KEY, this.getVariableMap())
                .execute();

        verify(this.templateScenario).hasCompleted("Task_ZustaendigkeitAendernOderVergabeVonRechtenBeauftragen");
        verify(this.processScenario).hasCompleted(TASK_KONTROLLIEREN);
        verify(this.processScenario).hasFinished(END_EVENT_BEENDET);
    }

    @Test
    public void shouldExecuteWithSachakteAnlegen() throws Exception {
        //Return null from searchSachakte
        when(this.dmsService.searchSachakte(any(), any())).thenReturn(Optional.empty());

        Scenario.run(this.processScenario)
                .startByKey(TEMPLATE_KEY, this.getVariableMap())
                .execute();

        verify(this.templateScenario).hasCompleted(VORGANG_ANLEGEN_V_1_SACHAKTE_ANLEGEN);
        verify(this.processScenario).hasCompleted(TASK_KONTROLLIEREN);
        verify(this.processScenario).hasFinished(END_EVENT_BEENDET);
    }

    @Test
    public void shouldExecuteWithSachakteAnlegenGesperrt() throws Exception {
        //Return null from searchSachakte
        when(this.dmsService.searchSachakte(any(), any())).thenReturn(Optional.empty());

        //Throw error
        when(this.dmsService.createSachakte(any(), any(), any()))
                .thenThrow(new DMSException(DMSStatusCode.OBJEKT_GESPERRT, "Gesperrt"))
                .thenReturn(Sachakte.builder().coo("SachakteCOO").build());

        Scenario.run(this.processScenario)
                .startByKey(TEMPLATE_KEY, this.getVariableMap())
                .execute();

        verify(this.templateScenario).hasCompleted(TASK_SPERRE_BETREFFSEINHEIT_AUFHEBEN);
        verify(this.processScenario).hasCompleted(TASK_KONTROLLIEREN);
        verify(this.processScenario).hasFinished(END_EVENT_BEENDET);
    }

    @Test
    public void shouldExecuteWithSachakteAnlegenBetreffseinheitAnlegen() throws Exception {
        //Return null from searchSachakte
        when(this.dmsService.searchSachakte(any(), any())).thenReturn(Optional.empty());

        //Throw error
        when(this.dmsService.createSachakte(any(), any(), any()))
                .thenThrow(new DMSException(DMSStatusCode.MEHR_ALS_1000_UNTERGEORDNETE_OBJEKTE, "Mehr als 1000 Objekte"))
                .thenReturn(Sachakte.builder().coo("SachakteCOO").build());

        Scenario.run(this.processScenario)
                .startByKey(TEMPLATE_KEY, this.getVariableMap())
                .execute();

        verify(this.templateScenario).hasCompleted(TASK_BETREFFSEINHEITEN_ANLEGEN);
        verify(this.processScenario).hasCompleted(TASK_KONTROLLIEREN);
        verify(this.processScenario).hasFinished(END_EVENT_BEENDET);
    }

    @Test
    public void shouldExecuteWithSachakteAnlegenFehlendeBerechtigung() throws Exception {
        //Return null from searchSachakte
        when(this.dmsService.searchSachakte(any(), any())).thenReturn(Optional.empty());

        //Throw error
        when(this.dmsService.createSachakte(any(), any(), any()))
                .thenThrow(new DMSException(DMSStatusCode.FEHLENDE_BERECHTIGUNG, "FehlendeBerechtigung"))
                .thenReturn(Sachakte.builder().coo("SachakteCOO").build());

        Scenario.run(this.processScenario)
                .startByKey(TEMPLATE_KEY, this.getVariableMap())
                .execute();

        verify(this.templateScenario).hasCompleted(TASK_SACHAKTE_ZUSTAENDIKEIT_AENDERN_ODER_VERGABE_VON_SCHREIBRECHTEN_BEAUFTRAGEN);
        verify(this.processScenario).hasCompleted(TASK_KONTROLLIEREN);
        verify(this.processScenario).hasFinished(END_EVENT_BEENDET);
    }

    @Test
    public void shouldExecuteWithVorgangAnlegenSperreAufheben() throws Exception {
        //Return null from searchSachakte
        when(this.dmsService.createVorgang(any(), any(), any()))
                .thenThrow(new DMSException(DMSStatusCode.OBJEKT_GESPERRT, "Gesperrt"))
                .thenReturn(Vorgang.builder().coo("VorgangCOO").build());

        Scenario.run(this.processScenario)
                .startByKey(TEMPLATE_KEY, this.getVariableMap())
                .execute();

        verify(this.templateScenario).hasCompleted(TASK_SPERRE_AKTE_AUFHEBEN);
        verify(this.processScenario).hasCompleted(TASK_KONTROLLIEREN);
        verify(this.processScenario).hasFinished(END_EVENT_BEENDET);
    }

    @Test
    public void shouldExecuteWithVorgangAnlegenFehlendeBerechtigung() throws Exception {
        //Return null from searchSachakte
        when(this.dmsService.createVorgang(any(), any(), any()))
                .thenThrow(new DMSException(DMSStatusCode.FEHLENDE_BERECHTIGUNG, "Fehlende Berechtigung"))
                .thenReturn(Vorgang.builder().coo("VorgangCOO").build());

        Scenario.run(this.processScenario)
                .startByKey(TEMPLATE_KEY, this.getVariableMap())
                .execute();

        verify(this.templateScenario).hasCompleted(TASK_VORGANG_ZUSTAENDIGKEIT_AENDERN_ODER_VERGABE_VON_SCHREIBRECHTEN_BEAUFTRAGEN);
        verify(this.processScenario).hasCompleted(TASK_KONTROLLIEREN);
        verify(this.processScenario).hasFinished(END_EVENT_BEENDET);
    }

    private Map<String, Object> getVariableMap() {
        return withVariables(
                VAR_STARTER_OF_INSTANCE, "starterOfInstance",
                VAR_FORM_FIELD_VORGANG_TITEL, "VorgangTitel",
                VAR_FORM_FIELD_UNTERGRUPPE, "untergruppe",
                VAR_FORM_FIELD_SACHAKTE_NAME, "sachakteName",
                VAR_FORM_FIELD_AKTENPLAN_NAME, "aktenplanName"
        );
    }

    private Map<String, Object> getS3VariableMap() {
        return withVariables(
                VAR_STARTER_OF_INSTANCE, "starterOfInstance",
                VAR_FORM_FIELD_VORGANG_TITEL, "VorgangTitel",
                VAR_FORM_FIELD_UNTERGRUPPE, "untergruppe",
                VAR_FORM_FIELD_SACHAKTE_NAME, "sachakteName",
                VAR_FORM_FIELD_AKTENPLAN_NAME, "aktenplanName",
                VAR_S3_URL, "customs3url"
        );
    }

    //    @Test
    //    public void shouldExecuteWithError() throws Exception {
    //
    //        doThrow(RuntimeException.class).when(this.dmsService).cancelDocument(any(), any());
    //
    //        Scenario.run(this.processScenario)
    //                .startByKey(TEMPLATE_KEY, withVariables(
    //                        VAR_DOKUMENTCOO, "VorgangCOO",
    //                        VAR_DMS_BENUTZER, "dmsBenutzer",
    //                        VAR_DMS_TASK_TITLE_MANUELL, "Mein Vorgang"
    //                ))
    //                .execute();
    //
    //        verify(this.templateScenario).hasCompleted(TASK_DOKUMENT_MANUELL_STORNIEREN);
    //
    //        verify(this.templateScenario).hasFinished(END_EVENT_DOKUMENT_STORNIERT);
    //    }

}
