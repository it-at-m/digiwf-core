/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.dms.bausteine.vorgangzalegen;

import de.muenchen.oss.digiwf.engine.basis.process.DigitalWFFunctions;
import de.muenchen.oss.digiwf.legacy.dms.muc.domain.service.DmsService;
import de.muenchen.oss.digiwf.legacy.dms.muc.external.transport.DMSException;
import de.muenchen.oss.digiwf.legacy.dms.muc.external.transport.DMSStatusCode;
import de.muenchen.oss.digiwf.legacy.dms.muc.process.depositvorgang.DepositVorgangDelegate;
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

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.withVariables;
import static org.mockito.Mockito.*;

@Deployment(resources = { "bausteine/dms/vorgangzalegen/VorgangZALegenV01.bpmn", "bausteine/dms/vorgangzalegen/feature/Feature_VorgangZALegen.bpmn" })
public class VorgangZAlegenTemplateTest {

    public static final String TEMPLATE_KEY = "FeatureVorgangZALegen";
    public static final String TASK_VORGANG_ZA_LEGEN = "Task_VorgangZALegen";
    public static final String TASK_VORGANG_MANUELL_ZU_DEN_AKTEN_LEGEN = "Task_VorgangManuellZuDenAktenLegen";
    public static final String END_EVENT_BEENDET = "EndEvent_Beendet";
    public static final String TASK_VORGANG_ZU_DEN_AKTEN_LEGEN = "Task_VorgangZuDenAktenLegen";
    public static final String FORM_FIELD_VORGANG_COO = "FormField_VorgangCOO";
    public static final String STARTER_OF_INSTANCE = "starterOfInstance";
    public static final String FORM_FIELD_TASK_TITEL_MANUELL = "FormField_TaskTitelManuell";
    public static final String EVENT_ERROR_SONSTIGE = "Event_Error_Sonstige";
    public static final String EVENT_ERROR_ADRESSE = "Event_Error_Adresse";
    public static final String EVENT_ERROR_FEHLENDE_BERECHTIGUNG = "Event_Error_Fehlende_Berechtigung";
    public static final String EVENT_ERROR_VORGANG_GESPERRT = "Event_Error_Vorgang_Gesperrt";

    @Rule
    public ProcessEngineRule rule = new ProcessEngineRule();

    @Mock
    private ProcessScenario processScenario;

    @Mock
    private ProcessScenario templateScenario;

    @Mock
    private DmsService dmsService;

    @Mock
    private UserFunctions userFunctions;

    @Mock
    private DigitalWFFunctions digitalWF;

    @Before
    public void defaultScenario() throws Exception {
        MockitoAnnotations.initMocks(this);

        Mocks.register("depositVorgangDelegate", new DepositVorgangDelegate(this.dmsService));
        doNothing().when(this.dmsService).depositVorgang(any(), any());
        Mocks.register("sendMailDelegate", new TestSendMailDelegate());

        Mocks.register("digitalwf", this.digitalWF);
        when(this.digitalWF.urlGruppenaufgaben()).thenReturn("myurl");

        Mocks.register("user", this.userFunctions);

        when(this.userFunctions.email(any())).thenReturn("externer.dl.horn@muenchen.de");

        when(this.processScenario.runsCallActivity(TASK_VORGANG_ZA_LEGEN))
                .thenReturn(Scenario.use(this.templateScenario));

        when(this.templateScenario.waitsAtUserTask(TASK_VORGANG_MANUELL_ZU_DEN_AKTEN_LEGEN))
                .thenReturn(TaskDelegate::complete);
    }

    @Test
    public void shouldExecuteHappyPath() {
        Scenario.run(this.processScenario)
                .startByKey(TEMPLATE_KEY, withVariables(
                        FORM_FIELD_VORGANG_COO, "VorgangCOO",
                        STARTER_OF_INSTANCE, "startUser",
                        FORM_FIELD_TASK_TITEL_MANUELL, "Manueller Titel"

                ))
                .execute();

        verify(this.templateScenario).hasCompleted(TASK_VORGANG_ZU_DEN_AKTEN_LEGEN);
        verify(this.processScenario).hasFinished(END_EVENT_BEENDET);
    }

    @Test
    public void shouldExecuteWithVorgangGesperrt() throws Exception {

        doThrow(new DMSException(DMSStatusCode.OBJEKT_GESPERRT, "Gesperrt")).when(this.dmsService).depositVorgang(any(), any());

        Scenario.run(this.processScenario)
                .startByKey(TEMPLATE_KEY, withVariables(
                        FORM_FIELD_VORGANG_COO, "VorgangCOO",
                        STARTER_OF_INSTANCE, "startUser",
                        FORM_FIELD_TASK_TITEL_MANUELL, "Manueller Titel"

                ))
                .execute();

        verify(this.templateScenario).hasCompleted(TASK_VORGANG_MANUELL_ZU_DEN_AKTEN_LEGEN);
        verify(this.templateScenario).hasCompleted(EVENT_ERROR_VORGANG_GESPERRT);
        verify(this.processScenario).hasFinished(END_EVENT_BEENDET);
    }

    @Test
    public void shouldExecuteWithFehlendeBerechtigung() throws Exception {

        doThrow(new DMSException(DMSStatusCode.FEHLENDE_BERECHTIGUNG, "Fehlende Berechtigung")).when(this.dmsService).depositVorgang(any(), any());

        Scenario.run(this.processScenario)
                .startByKey(TEMPLATE_KEY, withVariables(
                        FORM_FIELD_VORGANG_COO, "VorgangCOO",
                        STARTER_OF_INSTANCE, "startUser",
                        FORM_FIELD_TASK_TITEL_MANUELL, "Manueller Titel"

                ))
                .execute();

        verify(this.templateScenario).hasCompleted(TASK_VORGANG_MANUELL_ZU_DEN_AKTEN_LEGEN);
        verify(this.templateScenario).hasCompleted(EVENT_ERROR_FEHLENDE_BERECHTIGUNG);
        verify(this.processScenario).hasFinished(END_EVENT_BEENDET);
    }

    @Test
    public void shouldExecuteWithUngueltigeAdresse() throws Exception {

        doThrow(new DMSException(DMSStatusCode.UNGUELTIGE_ADRESSE, "Ungültige Adresse")).when(this.dmsService).depositVorgang(any(), any());

        Scenario.run(this.processScenario)
                .startByKey(TEMPLATE_KEY, withVariables(
                        FORM_FIELD_VORGANG_COO, "VorgangCOO",
                        STARTER_OF_INSTANCE, "startUser",
                        FORM_FIELD_TASK_TITEL_MANUELL, "Manueller Titel"

                ))
                .execute();

        verify(this.templateScenario).hasCompleted(TASK_VORGANG_MANUELL_ZU_DEN_AKTEN_LEGEN);
        verify(this.templateScenario).hasCompleted(EVENT_ERROR_ADRESSE);
        verify(this.processScenario).hasFinished(END_EVENT_BEENDET);
    }

    @Test
    public void shouldExecuteWithSonstigeFehler() throws Exception {

        doThrow(new DMSException(DMSStatusCode.FALSCHE_ZUGRIFFSDEFINITION, "Sonstige Fehler")).when(this.dmsService).depositVorgang(any(), any());

        Scenario.run(this.processScenario)
                .startByKey(TEMPLATE_KEY, withVariables(
                        FORM_FIELD_VORGANG_COO, "VorgangCOO",
                        STARTER_OF_INSTANCE, "startUser",
                        FORM_FIELD_TASK_TITEL_MANUELL, "Manueller Titel"

                ))
                .execute();

        verify(this.templateScenario).hasCompleted(TASK_VORGANG_MANUELL_ZU_DEN_AKTEN_LEGEN);
        verify(this.templateScenario).hasCompleted(EVENT_ERROR_SONSTIGE);
        verify(this.processScenario).hasFinished(END_EVENT_BEENDET);
    }

}
