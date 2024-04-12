package de.muenchen.oss.digiwf.engine.incidents;

import de.muenchen.oss.digiwf.email.api.DigiwfEmailApi;
import de.muenchen.oss.digiwf.email.model.Mail;
import de.muenchen.oss.digiwf.process.config.domain.mapper.ProcessConfigMapper;
import de.muenchen.oss.digiwf.process.config.domain.model.ProcessConfig;
import de.muenchen.oss.digiwf.process.config.domain.service.ProcessConfigService;
import de.muenchen.oss.digiwf.process.config.infrastructure.entity.ProcessConfigEntity;
import jakarta.mail.MessagingException;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.incident.IncidentContext;
import org.camunda.bpm.engine.impl.persistence.entity.IncidentEntity;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IncidentNotifierHandlerTest {

    private static final String CONFIG_KEY = "TestProcessConfig";

    @Mock
    private DigiwfEmailApi digiwfEmailApi;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private RuntimeService runtimeService;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private RepositoryService repositoryService;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ProcessConfigService processConfigService;

    @Mock
    private IncidentNotificationProperties incidentNotificationProperties;

    @Captor
    private ArgumentCaptor<Mail> incidentEntityCaptor;

    @InjectMocks
    @Spy()
    private IncidentNotifierHandlerMock incidentNotifierHandler;

    /**
     * Tests incident notifications for configured email addresses.
     *
     * @throws IOException        In case there is an issue with reading a file on the file system.
     * @throws MessagingException In case there is an issue with sending an e-mail to the notification receivers.
     */
    @Test
    void testIncidentNotifications() throws IOException, MessagingException {

        // Setup
        final IncidentContext incidentContext = mock(IncidentContext.class);
        final IncidentEntity incidentEntity = mock(IncidentEntity.class);
        when(incidentEntity.getProcessInstanceId()).thenReturn("");
        when(runtimeService
                .createProcessInstanceQuery()
                .processInstanceId(anyString())
                .singleResult()
                .getRootProcessInstanceId())
                .thenReturn("rootProcessInstanceId");
        when(runtimeService
                .createProcessInstanceQuery()
                .processInstanceId(anyString())
                .singleResult()
                .getProcessDefinitionId())
                .thenReturn(CONFIG_KEY + ":v42:12345");
        ProcessConfig.builder()
                .key("processDefinitionId");
        final String configContent = Files.readString(Paths.get("src/test/resources/dummy/Test.processconfig.json"));
        final ProcessConfigEntity processConfigEntity = ProcessConfigEntity.builder()
                .version("v1")
                .key(CONFIG_KEY)
                .config(configContent)
                .build();
        final ProcessConfig processConfig = new ProcessConfigMapper().map(processConfigEntity);
        when(processConfigService.getProcessConfig(anyString())).thenReturn(Optional.of(processConfig));
        when(incidentNotifierHandler.superHandleIncident(any(), anyString())).thenReturn(incidentEntity);
        when(incidentNotificationProperties.getCockpitUrl()).thenReturn("cockpiturl");
        when(incidentNotificationProperties.getEnvironment()).thenReturn("environment");
        when(incidentNotificationProperties.getFromAddress()).thenReturn("from@address.org");
        ProcessDefinition processDefinitionMock = Mockito.mock(ProcessDefinition.class);
        when(repositoryService.createProcessDefinitionQuery().processDefinitionId(anyString()).singleResult()).thenReturn(processDefinitionMock);

        // Execute
        incidentNotifierHandler.handleIncident(incidentContext, "Incident message");

        // Assert that an email was sent with the correct receivers.
        verify(digiwfEmailApi, times(1)).sendMailWithDefaultLogo(incidentEntityCaptor.capture());
        final Mail mail = incidentEntityCaptor.getValue();
        assertThat(mail.getReceivers()).isEqualTo(processConfig.getIncidentNotificationAddresses());
    }

    private static class IncidentNotifierHandlerMock extends IncidentNotifierHandler {

        public IncidentNotifierHandlerMock(RepositoryService repositoryService, RuntimeService runtimeService, DigiwfEmailApi digiwfEmailApi,
                ProcessConfigService processConfigService, IncidentNotificationProperties incidentNotificationProperties) {
            super(repositoryService, runtimeService, digiwfEmailApi, processConfigService, incidentNotificationProperties);
        }

        // Workaround: If a static method is in the chain, Mockito will not mock method calls of superclasses.
        @Override
        IncidentEntity superHandleIncident(final IncidentContext context, final String message) {
            return null;
        }
    }
}

