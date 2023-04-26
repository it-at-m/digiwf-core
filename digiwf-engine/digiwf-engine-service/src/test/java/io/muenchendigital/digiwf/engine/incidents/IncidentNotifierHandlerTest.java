/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2021
 */
package io.muenchendigital.digiwf.engine.incidents;

import io.muenchendigital.digiwf.legacy.mailing.domain.model.MailTemplate;
import io.muenchendigital.digiwf.legacy.mailing.domain.service.MailingService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.impl.persistence.entity.IncidentEntity;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.mockito.Mock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

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

    @Mock
    private ProcessDefinition processDefinition;

    @Mock
    private ProcessDefinitionQuery processDefinitionQuery;

    @Mock
    private MailingService mailingService;

    /**
     * Tests if processname is read from ProcessDefinition and wriiten into E-Mail
     *  */
    @Test
    public void testHandleIncident() throws Exception {


        Mockito.when(this.incidentEntity.getProcessDefinitionId()).thenReturn("Test123");
        Mockito.when(this.processDefinition.getKey()).thenReturn("Testprozess");
        Mockito.when(this.repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);when(processDefinitionQuery.singleResult()).thenReturn(processDefinition);
        Mockito.when(processDefinitionQuery.processDefinitionId(anyString())).thenReturn(processDefinitionQuery);

        this.incidentNotifierHandler.sendInfoMail(incidentEntity);

        ArgumentCaptor<MailTemplate> argument = ArgumentCaptor.forClass(MailTemplate.class);
        verify(mailingService).sendMailTemplateWithLink(argument.capture());
        assertTrue(argument.getValue().getBody().contains("In der Anwendung ist ein Incident aufgetreten (Prozessname: Testprozess)."));
    }
}
