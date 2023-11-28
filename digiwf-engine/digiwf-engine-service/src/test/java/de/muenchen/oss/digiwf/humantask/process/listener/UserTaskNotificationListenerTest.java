/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2021
 */
package de.muenchen.oss.digiwf.humantask.process.listener;

import de.muenchen.oss.digiwf.email.api.DigiwfEmailApi;
import de.muenchen.oss.digiwf.email.model.Mail;
import de.muenchen.oss.digiwf.legacy.user.domain.model.User;
import de.muenchen.oss.digiwf.legacy.user.domain.service.UserService;
import de.muenchen.oss.digiwf.shared.properties.DigitalWFProperties;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.camunda.bpm.engine.task.IdentityLink;
import org.camunda.bpm.engine.task.IdentityLinkType;
import org.camunda.community.mockito.QueryMocks;
import org.camunda.community.mockito.process.ProcessDefinitionFake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

/**
 * Tests for UserTaskNotificationListener.
 *
 * @author martin.dietrich
 */
class UserTaskNotificationListenerTest {


    private final RepositoryService repositoryService = mock(RepositoryService.class);
    private final DigiwfEmailApi digiwfEmailApi = mock(DigiwfEmailApi.class);
    private final UserService userService = mock(UserService.class);
    private final DigitalWFProperties properties = mock(DigitalWFProperties.class);

    private UserTaskNotificationListener userTaskNotificationListener;

    // test data
    private User user;
    private User candidate;


    @BeforeEach
    void setup() {
        this.userTaskNotificationListener = new UserTaskNotificationListener(repositoryService, digiwfEmailApi, userService, properties);

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
    }

    /**
     * Tests if no notification is sent out when all notification switches are off.
     */
    @Test
    void testDelegateTask_WithNotificationOff() throws Exception {
        final DelegateTask task = this.prepareDelegateTask(Map.of(
                "digitalwf_notification_send_assignee", "false",
                "digitalwf_notification_send_candidate_users", "false",
                "digitalwf_notification_send_candidate_groups", "false",
                "app_task_assignee", this.user.getLhmObjectId()
        ));
        this.userTaskNotificationListener.delegateTask(task);
        verify(this.digiwfEmailApi, times(0)).sendMailWithDefaultLogo(ArgumentMatchers.any(Mail.class));
    }

    /**
     * Tests if a notification to the assignee is sent out when notification is on.
     */
    @Test
    void testDelegateTask_WithAssignee() throws Exception {
        final DelegateTask task = this.prepareDelegateTask(Map.of(
                "digitalwf_notification_send_assignee", "true",
                "digitalwf_notification_send_candidate_users", "false",
                "digitalwf_notification_send_candidate_groups", "false",
                "app_task_assignee", this.user.getLhmObjectId()
        ));
        when(task.getCandidates()).thenReturn(Collections.<IdentityLink>emptySet());

        // execute
        this.userTaskNotificationListener.delegateTask(task);

        final ArgumentCaptor<Mail> argument = ArgumentCaptor.forClass(Mail.class);
        verify(this.digiwfEmailApi, times(1)).sendMailWithDefaultLogo(argument.capture());
        assertThat(argument.getValue().getReceivers()).isEqualTo(this.user.getEmail());
    }

    /**
     * Tests if a notification to the assignee and candidate users is sent out when notification is on.
     * Process name should be read from ProcessDefinition.Key while ProcessDefinition.Name is not set
     * and be written to Notification E-Mail.
     */
    @Test
    void testDelegateTask_WithAssigneeAndCandidateUsers() throws Exception {
        final DelegateTask task = this.prepareDelegateTask(Map.of(
                "digitalwf_notification_send_assignee", "true",
                "digitalwf_notification_send_candidate_users", "true",
                "digitalwf_notification_send_candidate_groups", "false",
                "app_task_assignee", this.user.getLhmObjectId()
        ));
        HashSet<IdentityLink> candidateSet = new HashSet<IdentityLink>();
        IdentityLink identityLink = mock(IdentityLink.class);
        when(identityLink.getUserId()).thenReturn(this.candidate.getLhmObjectId());
        when(identityLink.getType()).thenReturn(IdentityLinkType.CANDIDATE);
        candidateSet.add(identityLink);
        when(task.getCandidates()).thenReturn(candidateSet);

        ProcessDefinitionQuery query = QueryMocks.mockProcessDefinitionQuery(repositoryService).singleResult(ProcessDefinitionFake.builder()
                .key("Testprozess-key").build());

        // execute
        this.userTaskNotificationListener.delegateTask(task);

        verify(query, times(2)).processDefinitionId(task.getProcessDefinitionId());

        final ArgumentCaptor<Mail> argument = ArgumentCaptor.forClass(Mail.class);
        verify(this.digiwfEmailApi, times(2)).sendMailWithDefaultLogo(argument.capture());

        final List<Mail> arguments = argument.getAllValues();
        assertThat(arguments)
                .extracting("receivers")
                .isEqualTo(List.of(user.getEmail(), candidate.getEmail()));
        // TODO proper test body
//        assertThat(arguments)
//                .extracting("body")
//                .isEqualTo(List.of("Sie haben eine Aufgabe in DigiWF (Testprozess-key).", "Sie haben eine Gruppenaufgabe in DigiWF (Testprozess-key)."));
    }

    /**
     * Tests if a notification to the assignee and process name is read from ProcessDefinition.Name.
     */
    @Test
    void testDelegateTask_WithAssignee_AndProcessName_ReadFromProcessDefinition() throws Exception {
        final DelegateTask task = this.prepareDelegateTask(Map.of(
                "digitalwf_notification_send_assignee", "true",
                "digitalwf_notification_send_candidate_users", "false",
                "digitalwf_notification_send_candidate_groups", "false",
                "app_task_assignee", this.user.getLhmObjectId()
        ));

        HashSet<IdentityLink> candidateSet = new HashSet<IdentityLink>();
        IdentityLink identityLink = mock(IdentityLink.class);
        when(identityLink.getUserId()).thenReturn(this.candidate.getLhmObjectId());
        when(identityLink.getType()).thenReturn(IdentityLinkType.CANDIDATE);
        candidateSet.add(identityLink);
        when(task.getCandidates()).thenReturn(candidateSet);

        when(userService.getUser(this.candidate.getLhmObjectId())).thenReturn(candidate);

        ProcessDefinitionQuery query = QueryMocks.mockProcessDefinitionQuery(repositoryService).singleResult(ProcessDefinitionFake.builder()
                .key("Testprozess-name").build());

        // execute
        this.userTaskNotificationListener.delegateTask(task);

        verify(query, times(1)).processDefinitionId(task.getProcessDefinitionId());

        final ArgumentCaptor<Mail> argument = ArgumentCaptor.forClass(Mail.class);
        verify(this.digiwfEmailApi, times(1)).sendMailWithDefaultLogo(argument.capture());

        assertThat(argument.getValue().getReceivers()).isEqualTo(user.getEmail());
        // TODO proper test body
//        assertThat(argument.getValue().getBody()).isEqualTo("Sie haben eine Aufgabe in DigiWF (Testprozess-name).");
    }

    /**
     * Tests if a notification to the candidate users is sent out when notification is on.
     */
    @Test
    void testDelegateTask_WithCandidateUsers() throws Exception {
        final DelegateTask task = this.prepareDelegateTask(Map.of(
                "digitalwf_notification_send_assignee", "false",
                "digitalwf_notification_send_candidate_users", "true",
                "digitalwf_notification_send_candidate_groups", "false",
                "app_task_assignee", null
        ));

        HashSet<IdentityLink> candidateSet = new HashSet<IdentityLink>();
        IdentityLink identityLink1 = mock(IdentityLink.class);
        when(identityLink1.getUserId()).thenReturn(this.user.getLhmObjectId());
        when(identityLink1.getType()).thenReturn(IdentityLinkType.CANDIDATE);
        candidateSet.add(identityLink1);
        IdentityLink identityLink2 = mock(IdentityLink.class);
        when(identityLink2.getUserId()).thenReturn(this.candidate.getLhmObjectId());
        when(identityLink2.getType()).thenReturn(IdentityLinkType.CANDIDATE);
        candidateSet.add(identityLink2);
        when(task.getCandidates()).thenReturn(candidateSet);

        // execute
        this.userTaskNotificationListener.delegateTask(task);

        // check if service is called with defined mail addresses
        final ArgumentCaptor<Mail> argument = ArgumentCaptor.forClass(Mail.class);
        verify(this.digiwfEmailApi, times(1)).sendMailWithDefaultLogo(argument.capture());

        assertThat(argument.getValue().getReceivers()).contains(this.user.getEmail());
        assertThat(argument.getValue().getReceivers()).contains(this.candidate.getEmail());
    }

    /**
     * Tests that no notification is sent out when notification is on but no assignee/candidates are defined.
     */
    @Test
    void testDelegateTask_WithoutUsers() throws Exception {
        final DelegateTask task = this.prepareDelegateTask(Map.of(
                "digitalwf_notification_send_assignee", "true",
                "digitalwf_notification_send_candidate_users", "true",
                "digitalwf_notification_send_candidate_groups", "true"
        ));

        // execute
        this.userTaskNotificationListener.delegateTask(task);

        verify(this.digiwfEmailApi, times(0)).sendMailWithDefaultLogo(any(Mail.class));
    }

    /**
     * Tests if a notification to the candidate groups is send out when notification is on.
     */
    @Test
    void testDelegateTask_WithCandidateGroups() throws Exception {
        final String groupName1 = "itm-km82";
        final String groupName2 = "itm-km83";
        final DelegateTask task = this.prepareDelegateTask(Map.of(
                "digitalwf_notification_send_assignee", "false",
                "digitalwf_notification_send_candidate_users", "false",
                "digitalwf_notification_send_candidate_groups", "true",
                "app_task_assignee", null
        ));

        HashSet<IdentityLink> candidateSet = new HashSet<IdentityLink>();
        IdentityLink identityLink1 = mock(IdentityLink.class);
        when(identityLink1.getGroupId()).thenReturn(groupName1);
        when(identityLink1.getType()).thenReturn(IdentityLinkType.CANDIDATE);
        candidateSet.add(identityLink1);
        IdentityLink identityLink2 = mock(IdentityLink.class);
        when(identityLink2.getGroupId()).thenReturn(groupName2);
        when(identityLink2.getType()).thenReturn(IdentityLinkType.CANDIDATE);
        candidateSet.add(identityLink2);
        when(task.getCandidates()).thenReturn(candidateSet);

        when(userService.getOuByShortName(groupName1)).thenReturn(Optional.of(this.user));
        when(userService.getOuByShortName(groupName2)).thenReturn(Optional.of(this.candidate));

        // execute
        this.userTaskNotificationListener.delegateTask(task);

        final ArgumentCaptor<Mail> argument = ArgumentCaptor.forClass(Mail.class);
        verify(this.digiwfEmailApi, times(1)).sendMailWithDefaultLogo(argument.capture());

        assertThat(argument.getValue().getReceivers()).contains(this.user.getEmail());
        assertThat(argument.getValue().getReceivers()).contains(this.candidate.getEmail());
    }

    /**
     * Tests if a notification to the assignee is send out with the default mail subject, body and bottom text.
     */
    @Test
    void testDelegateTask_WithAssigneeAndDefaultSubjectBodyAndBottomText() throws Exception {
        final DelegateTask task = this.prepareDelegateTask(Map.of(
                "digitalwf_notification_send_assignee", "true",
                "digitalwf_notification_send_candidate_users", "false",
                "digitalwf_notification_send_candidate_groups", "false",
                "app_task_assignee", this.user.getLhmObjectId()
        ));
        when(task.getCandidates()).thenReturn(Collections.<IdentityLink>emptySet());

        // execute
        this.userTaskNotificationListener.delegateTask(task);

        // check if service is called with defined mail addresses
        final ArgumentCaptor<Mail> argument = ArgumentCaptor.forClass(Mail.class);
        verify(this.digiwfEmailApi, times(1)).sendMailWithDefaultLogo(argument.capture());

        assertThat(argument.getValue().getReceivers()).isEqualTo(this.user.getEmail());
        assertThat(argument.getValue().getSubject()).isEqualTo("Es liegt eine neue Aufgabe für Sie bereit");
        // TODO proper test body
//        assertThat(argument.getValue().getBody()).isEqualTo("Sie haben eine Aufgabe in DigiWF.");
    }

    /**
     * Tests if a notification to the assignee is send out with the customized mail subject, body and bottom text.
     */
    @Test
    void testDelegateTask_WithAssigneeAndCustomizedSubjectBodyAndBottomText() throws Exception {
        final DelegateTask task = this.prepareDelegateTask(Map.of(
                "digitalwf_notification_send_assignee", "true",
                "digitalwf_notification_send_candidate_users", "false",
                "digitalwf_notification_send_candidate_groups", "false",
                "app_task_assignee", this.user.getLhmObjectId(),
                "mail_subject", "Neue Testaufgabe",
                "mail_body", "Hier kommen Sie zu der neuen Testaufgabe.",
                "mail_bottom_text", "Viele Grüße"
        ));
        when(task.getCandidates()).thenReturn(Collections.<IdentityLink>emptySet());

        // execute
        this.userTaskNotificationListener.delegateTask(task);

        final ArgumentCaptor<Mail> argument = ArgumentCaptor.forClass(Mail.class);
        verify(this.digiwfEmailApi, times(1)).sendMailWithDefaultLogo(argument.capture());

        assertThat(argument.getValue().getReceivers()).isEqualTo(this.user.getEmail());
        assertThat(argument.getValue().getSubject()).isEqualTo("Neue Testaufgabe");
        // TODO proper test body
//        assertThat(argument.getValue().getBody()).contains("Hier kommen Sie zu der neuen Testaufgabe.");
//        assertThat(argument.getValue().getBody()).contains("Viele Grüße");
    }

    /**
     * Tests if a notification to the candidate users and groups is send out with the default mail subject, body and bottom text.
     */
    @Test
    void testDelegateTask_WithCandidateUsersAndCandidateGroupsAndDefaultSubjectBodyAndBottomText() throws Exception {
        final String groupName = "itm-km82";
        final DelegateTask task = this.prepareDelegateTask(Map.of(
                "digitalwf_notification_send_assignee", "false",
                "digitalwf_notification_send_candidate_users", "true",
                "digitalwf_notification_send_candidate_groups", "true",
                "app_task_assignee", this.user.getLhmObjectId()
        ));
        HashSet<IdentityLink> candidateSet = new HashSet<IdentityLink>();
        IdentityLink identityLink1 = mock(IdentityLink.class);
        when(identityLink1.getUserId()).thenReturn(this.user.getLhmObjectId());
        when(identityLink1.getType()).thenReturn(IdentityLinkType.CANDIDATE);
        candidateSet.add(identityLink1);
        IdentityLink identityLink2 = mock(IdentityLink.class);
        when(identityLink2.getGroupId()).thenReturn(groupName);
        when(identityLink2.getType()).thenReturn(IdentityLinkType.CANDIDATE);
        candidateSet.add(identityLink2);
        when(task.getCandidates()).thenReturn(candidateSet);

        when(userService.getOuByShortName(groupName)).thenReturn(Optional.of(this.candidate));

        // execute
        this.userTaskNotificationListener.delegateTask(task);

        final ArgumentCaptor<Mail> argument = ArgumentCaptor.forClass(Mail.class);
        verify(this.digiwfEmailApi, times(2)).sendMailWithDefaultLogo(argument.capture());

        assertThat(argument.getValue().getReceivers()).isEqualTo(List.of(this.user.getEmail(), this.candidate.getEmail()));
        // TODO proper test body
//        assertThat(argument.getValue().getSubject()).isEqualTo("Es liegt eine neue Gruppenaufgabe für Sie bereit");
//        assertThat(argument.getValue().getBody()).contains("Sie haben eine Gruppenaufgabe in DigiWF.");
    }

    /**
     * Tests if a notification to the candidate users and groups is send out with the customized mail subject, body and bottom text.
     */
    @Test
    void testDelegateTask_WithCandidateUsersAndCandidateGroupsAndCustomizedSubjectBodyAndBottomText() throws Exception {
        final String groupName = "itm-km82";
        final DelegateTask task = this.prepareDelegateTask(Map.of(
                "digitalwf_notification_send_assignee", "false",
                "digitalwf_notification_send_candidate_users", "true",
                "digitalwf_notification_send_candidate_groups", "true",
                "app_task_assignee", null,
                "mail_subject", "Neue Testaufgabe",
                "mail_body", "Hier kommen Sie zu der neuen Testaufgabe.",
                "mail_bottom_text", "Viele Grüße"
        ));
        HashSet<IdentityLink> candidateSet = new HashSet<IdentityLink>();
        IdentityLink identityLink1 = mock(IdentityLink.class);
        when(identityLink1.getUserId()).thenReturn(this.user.getLhmObjectId());
        when(identityLink1.getType()).thenReturn(IdentityLinkType.CANDIDATE);
        candidateSet.add(identityLink1);
        IdentityLink identityLink2 = mock(IdentityLink.class);
        when(identityLink2.getGroupId()).thenReturn(groupName);
        when(identityLink2.getType()).thenReturn(IdentityLinkType.CANDIDATE);
        candidateSet.add(identityLink2);
        when(task.getCandidates()).thenReturn(candidateSet);

        when(userService.getOuByShortName(groupName)).thenReturn(Optional.of(this.candidate));

        // execute
        this.userTaskNotificationListener.delegateTask(task);

        final ArgumentCaptor<Mail> argument = ArgumentCaptor.forClass(Mail.class);
        verify(this.digiwfEmailApi, times(2)).sendMailWithDefaultLogo(argument.capture());

        assertThat(argument.getValue().getReceivers()).isEqualTo(List.of(this.user.getEmail(), this.candidate.getEmail()));
        assertThat(argument.getValue().getSubject()).isEqualTo("Neue Testaufgabe");
        // TODO proper test body
//        assertThat(argument.getValue().getBody()).contains("Hier kommen Sie zu der neuen Testaufgabe.");
//        assertThat(argument.getValue().getBody()).contains("Viele Grüße");
    }

    private DelegateTask prepareDelegateTask(final Map<String, String> variables) {
        final DelegateTask task = mock(DelegateTask.class);
        for(Map.Entry<String, String> entry : variables.entrySet()) {
            when(task.getVariable(entry.getKey())).thenReturn(entry.getValue());
        }
        when(task.getEventName()).thenReturn("create");
        when(task.getProcessDefinitionId()).thenReturn("test123");
        return task;
    }
}
