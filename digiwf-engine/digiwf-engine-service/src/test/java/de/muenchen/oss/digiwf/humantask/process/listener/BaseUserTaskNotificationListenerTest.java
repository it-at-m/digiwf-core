package de.muenchen.oss.digiwf.humantask.process.listener;

import de.muenchen.oss.digiwf.email.api.DigiwfEmailApi;
import de.muenchen.oss.digiwf.email.model.Mail;
import de.muenchen.oss.digiwf.legacy.user.domain.model.User;
import de.muenchen.oss.digiwf.legacy.user.domain.service.UserService;
import de.muenchen.oss.digiwf.shared.properties.DigitalWFProperties;
import jakarta.mail.MessagingException;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.task.IdentityLink;
import org.camunda.bpm.engine.task.IdentityLinkType;
import org.mockito.ArgumentCaptor;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public abstract class BaseUserTaskNotificationListenerTest {

    private final RepositoryService repositoryService = mock(RepositoryService.class);
    private final DigiwfEmailApi digiwfEmailApi = mock(DigiwfEmailApi.class);
    private final UserService userService = mock(UserService.class);
    private final DigitalWFProperties properties = mock(DigitalWFProperties.class);

    private UserTaskNotificationListener userTaskNotificationListener;

    // test data
    final String defaultEmailBody = "Default Email Body";
    final String frontendUrl = "http://localhost:8080/digiwf";
    final String taskId = "1234";
    final String groupName = "group1";
    User user;
    User candidate;
    Set<IdentityLink> groupCandidates;
    Set<IdentityLink> userCandidates;


    void setup() {
        this.userTaskNotificationListener = new UserTaskNotificationListener(repositoryService, digiwfEmailApi, userService, properties);

        when(this.properties.getFrontendUrl()).thenReturn(frontendUrl);
        when(this.digiwfEmailApi.getEmailBodyFromTemplate(anyString(), anyMap())).thenReturn(defaultEmailBody);

        // test data
        this.user = new User();
        this.user.setUsername("flash.gordon");
        this.user.setLhmObjectId("123456789");
        this.user.setEmail("flash.gordon@muenchen.de");
        when(userService.getUser("123456789")).thenReturn(user);

        this.candidate = new User();
        this.candidate.setUsername("dale.arden");
        this.candidate.setLhmObjectId("987654321");
        this.candidate.setEmail("dale.arden@muenchen.de");
        when(userService.getUser("987654321")).thenReturn(candidate);

        when(this.userService.getOuByShortName(this.groupName)).thenReturn(Optional.of(this.candidate));

        this.groupCandidates = new HashSet<>();
        IdentityLink identityLink1 = mock(IdentityLink.class);
        when(identityLink1.getGroupId()).thenReturn(groupName);
        when(identityLink1.getType()).thenReturn(IdentityLinkType.CANDIDATE);
        groupCandidates.add(identityLink1);

        this.userCandidates = new HashSet<>();
        IdentityLink identityLink2 = mock(IdentityLink.class);
        when(identityLink2.getUserId()).thenReturn(this.candidate.getLhmObjectId());
        when(identityLink2.getType()).thenReturn(IdentityLinkType.CANDIDATE);
        userCandidates.add(identityLink2);
    }

    Mail notifyUsers(final DelegateTask delegateTask, final Map<String, String> mailContent, final int amountMailsSent) throws Exception {
        // execute
        this.userTaskNotificationListener.delegateTask(delegateTask);

        if (amountMailsSent != 0) {
            this.verifyEmailBody("bausteine/mail/templatewithlink/mail-template.tpl", mailContent);
        }
        return this.verifyThatMailIsSent(amountMailsSent);
    }



    DelegateTask prepareDelegateTask(final Map<String, String> variables) {
        final DelegateTask task = mock(DelegateTask.class);
        for(Map.Entry<String, String> entry : variables.entrySet()) {
            when(task.getVariable(entry.getKey())).thenReturn(entry.getValue());
        }
        when(task.getEventName()).thenReturn("create");
        when(task.getProcessDefinitionId()).thenReturn("test123");
        when(task.getId()).thenReturn(taskId);
        return task;
    }

    private Mail verifyThatMailIsSent(final int amountMailsSent) throws MessagingException {
        if (amountMailsSent == 0) {
            verify(this.digiwfEmailApi, times(0)).sendMailWithDefaultLogo(any());
            return null;
        }

        final ArgumentCaptor<Mail> argument = ArgumentCaptor.forClass(Mail.class);
        verify(this.digiwfEmailApi, times(amountMailsSent)).sendMailWithDefaultLogo(argument.capture());
        return  argument.getValue();
    }

    private void verifyEmailBody(String templatePath, Map<String, String> content) {
        final ArgumentCaptor<String> templatePathCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<Map<String, String>> contentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(this.digiwfEmailApi, times(1)).getEmailBodyFromTemplate(templatePathCaptor.capture(), contentCaptor.capture());
        assertThat(templatePathCaptor.getValue()).isEqualTo(templatePath);
        assertThat(contentCaptor.getValue()).isEqualTo(content);
    }

}
