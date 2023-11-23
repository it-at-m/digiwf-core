/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2021
 */
package de.muenchen.oss.digiwf.engine.incidents;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.impl.persistence.entity.IncidentEntity;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Tests for IncidentNtifierHandler.
 *
 * @author christian.slawinger
 */
@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class IncidentNotifierHandlerTest {

    @InjectMocks
    private IncidentNotifierHandler incidentNotifierHandler;

    @Mock
    private RepositoryService repositoryService;

    @Mock
    private IncidentEntity incidentEntity;

//    @Mock
//    private MailingService mailingService;

//    /**
//     * Tests if processname is read from ProcessDefinition and wriiten into E-Mail
//     *  */
//    @Test
//    public void handlesIncidentWithProcessDefinitionWithKey() throws Exception {
//
//        ProcessDefinitionQuery query = QueryMocks.mockProcessDefinitionQuery(repositoryService).singleResult(ProcessDefinitionFake.builder().key("Testprozess-key").id("Test123").build());
//        Mockito.when(this.incidentEntity.getProcessDefinitionId()).thenReturn("Test123");
//
//        this.incidentNotifierHandler.sendInfoMail(incidentEntity);
//
//        verify(query).processDefinitionId(this.incidentEntity.getProcessDefinitionId());
//        ArgumentCaptor<MailTemplate> argument = ArgumentCaptor.forClass(MailTemplate.class);
//        verify(mailingService).sendMailTemplateWithLink(argument.capture());
//        assertThat(argument.getValue().getBody()).isEqualTo("In der Anwendung ist ein Incident aufgetreten (Prozessname: Testprozess-key).");
//    }
//
//    @Test
//    public void handlesIncidentWithProcessDefinitionWithName() throws Exception {
//
//        ProcessDefinitionQuery query = QueryMocks.mockProcessDefinitionQuery(repositoryService).singleResult(ProcessDefinitionFake.builder().name("Testprozess1")
//                .key("Testprozess-key").id("Test123").build());
//        Mockito.when(this.incidentEntity.getProcessDefinitionId()).thenReturn("Test123");
//
//        this.incidentNotifierHandler.sendInfoMail(incidentEntity);
//
//        verify(query).processDefinitionId(this.incidentEntity.getProcessDefinitionId());
//        ArgumentCaptor<MailTemplate> argument = ArgumentCaptor.forClass(MailTemplate.class);
//        verify(mailingService).sendMailTemplateWithLink(argument.capture());
//        assertThat(argument.getValue().getBody()).isEqualTo("In der Anwendung ist ein Incident aufgetreten (Prozessname: Testprozess1).");
//
//    }
}
