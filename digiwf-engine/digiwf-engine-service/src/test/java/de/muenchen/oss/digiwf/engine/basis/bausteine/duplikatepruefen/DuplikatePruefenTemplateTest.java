/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.engine.basis.bausteine.duplikatepruefen;

import de.muenchen.oss.digiwf.engine.basis.process.businesskeyerstellen.CreateBusinessKeyDelegate;
import de.muenchen.oss.digiwf.engine.basis.process.duplikatepruefen.CheckBusinessKeyDelegate;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Deployment(resources = { "bausteine/basis/duplikatepruefen/DuplikatePruefenV01.bpmn",
        "bausteine/basis/duplikatepruefen/feature/Feature_DuplikatePruefen.bpmn" })
public class DuplikatePruefenTemplateTest {

    public static final String TEMPLATE_KEY = "FeatureDuplikatePruefen";
    public static final String ACTIVITY_DOKUMENT_DUPLIKATE_PRUEFEN = "Activity_DokumentDuplikatePruefen";
    public static final String TASK_DUPLIKATE_PRUEFEN_ABSCHLIESSEN = "Task_DuplikatePruefenAbschliessen";
    public static final String TASK_DUPLIKAT_KONTROLLIEREN = "Task_DuplikatKontrollieren";
    public static final String TASK_DUPLIKATE_PRUEFEN = "Task_DuplikatePruefen";
    public static final String EVENT_PRUEFEUNG_BEENDET = "Event_PruefeungBeendet";
    public static final String STARTER_OF_INSTANCE = "starterOfInstance";
    public static final String FORM_FIELD_VARIABLE_1 = "FormField_Variable1";
    public static final String FORM_FIELD_VARIABLE_2 = "FormField_Variable2";
    public static final String FORM_FIELD_CANCEL_PROCESS = "FormField_CancelProcess";
    public static final String EVENT_END_ERROR = "Event_End_Error";

    @Rule
    public ProcessEngineRule rule = new ProcessEngineRule();

    @Mock
    private ProcessScenario processScenario;

    @Mock
    private ProcessScenario templateScenario;

    @Before
    public void defaultScenario() throws Exception {
        MockitoAnnotations.initMocks(this);

        Mocks.register("createBusinessKeyDelegate", new CreateBusinessKeyDelegate());

        Mocks.register("checkBusinessKeyDelegate", new CheckBusinessKeyDelegate(this.rule.getRuntimeService(), this.rule.getHistoryService()));

        when(this.processScenario.runsCallActivity(ACTIVITY_DOKUMENT_DUPLIKATE_PRUEFEN))
                .thenReturn(Scenario.use(this.templateScenario));

        when(this.processScenario.waitsAtUserTask(TASK_DUPLIKATE_PRUEFEN_ABSCHLIESSEN))
                .thenReturn(TaskDelegate::complete);

        when(this.templateScenario.waitsAtUserTask(TASK_DUPLIKAT_KONTROLLIEREN))
                .thenReturn(TaskDelegate::complete);

    }

    @Test
    public void shouldExecuteHappyPath() {
        Scenario.run(this.processScenario)
                .startByKey(TEMPLATE_KEY, withVariables(
                        STARTER_OF_INSTANCE, "startUser",
                        FORM_FIELD_VARIABLE_1, "1",
                        FORM_FIELD_VARIABLE_2, "2"
                ))
                .execute();

        verify(this.processScenario).hasCompleted(TASK_DUPLIKATE_PRUEFEN_ABSCHLIESSEN);
        verify(this.templateScenario).hasCompleted(TASK_DUPLIKATE_PRUEFEN);
        verify(this.templateScenario).hasFinished(EVENT_PRUEFEUNG_BEENDET);
    }

    @Test
    public void shouldExecuteWithDuplicate() {

        this.rule.getRuntimeService().startProcessInstanceByKey(TEMPLATE_KEY, withVariables(
                STARTER_OF_INSTANCE, "startUser",
                FORM_FIELD_VARIABLE_1, "1",
                FORM_FIELD_VARIABLE_2, "2"
        ));

        Scenario.run(this.processScenario)
                .startByKey(TEMPLATE_KEY, withVariables(
                        STARTER_OF_INSTANCE, "startUser",
                        FORM_FIELD_VARIABLE_1, "1",
                        FORM_FIELD_VARIABLE_2, "2"
                ))
                .execute();

        verify(this.processScenario).hasCompleted(TASK_DUPLIKATE_PRUEFEN_ABSCHLIESSEN);
        verify(this.templateScenario).hasCompleted(TASK_DUPLIKATE_PRUEFEN);
        verify(this.templateScenario).hasCompleted(TASK_DUPLIKAT_KONTROLLIEREN);
        verify(this.templateScenario).hasFinished(EVENT_PRUEFEUNG_BEENDET);
    }

    @Test
    public void shouldExecuteWithDuplicateAndCancel() {

        when(this.templateScenario.waitsAtUserTask(TASK_DUPLIKAT_KONTROLLIEREN))
                .thenReturn(task -> task.complete(withVariables(FORM_FIELD_CANCEL_PROCESS, true)));

        this.rule.getRuntimeService().startProcessInstanceByKey(TEMPLATE_KEY, withVariables(
                STARTER_OF_INSTANCE, "startUser",
                FORM_FIELD_VARIABLE_1, "1",
                FORM_FIELD_VARIABLE_2, "2"
        ));

        Scenario.run(this.processScenario)
                .startByKey(TEMPLATE_KEY, withVariables(
                        STARTER_OF_INSTANCE, "startUser",
                        FORM_FIELD_VARIABLE_1, "1",
                        FORM_FIELD_VARIABLE_2, "2"
                ))
                .execute();

        verify(this.processScenario).hasCompleted(EVENT_END_ERROR);
        verify(this.templateScenario).hasCompleted(TASK_DUPLIKATE_PRUEFEN);
        verify(this.templateScenario).hasCompleted(TASK_DUPLIKAT_KONTROLLIEREN);
        verify(this.templateScenario).hasFinished(EVENT_PRUEFEUNG_BEENDET);
    }

}
