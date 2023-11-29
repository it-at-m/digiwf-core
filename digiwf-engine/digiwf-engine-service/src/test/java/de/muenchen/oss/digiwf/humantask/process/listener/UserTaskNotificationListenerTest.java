/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2021
 */
package de.muenchen.oss.digiwf.humantask.process.listener;

import de.muenchen.oss.digiwf.email.model.Mail;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.task.IdentityLink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

/**
 * Tests for UserTaskNotificationListener.
 *
 * @author martin.dietrich
 */
class UserTaskNotificationListenerTest extends BaseUserTaskNotificationListenerTest {

    private final Map<String, String> userTaskDefaultMailContent =  Map.of(
            "%%body_top%%", "Sie haben eine Aufgabe in DigiWF.",
            "%%body_bottom%%", "",
            "%%button_link%%", this.frontendUrl + "/#/task/" + this.taskId,
            "%%button_text%%", "Aufgabe öffnen",
            "%%footer%%", "DigiWF 2.0<br>IT-Referat der Stadt München"
    );
    private final Map<String, String> groupTaskDefaultMailContent =  Map.of(
            "%%body_top%%", "Sie haben eine Gruppenaufgabe in DigiWF.",
            "%%body_bottom%%", "",
            "%%button_link%%", this.frontendUrl + "/#/opengrouptask/" + this.taskId,
            "%%button_text%%", "Gruppenaufgabe öffnen",
            "%%footer%%", "DigiWF 2.0<br>IT-Referat der Stadt München"
    );

    @BeforeEach
    void setup() {
        super.setup();
    }

    @Test
    void testDelegateTask_WithNotificationOff() throws Exception {
        final DelegateTask task = this.prepareDelegateTask(Map.of(
                "digitalwf_notification_send_assignee", "false",
                "digitalwf_notification_send_candidate_users", "false",
                "digitalwf_notification_send_candidate_groups", "false",
                "app_task_assignee", this.user.getLhmObjectId()
        ));
        this.notifyUsers(task, null, 0);
    }

    @Test
    void testDelegateTask_WithAssignee() throws Exception {
        final DelegateTask task = this.prepareDelegateTask(Map.of(
                "digitalwf_notification_send_assignee", "true",
                "digitalwf_notification_send_candidate_users", "false",
                "digitalwf_notification_send_candidate_groups", "false",
                "app_task_assignee", this.user.getLhmObjectId()
        ));
        when(task.getCandidates()).thenReturn(Collections.<IdentityLink>emptySet());

        final Mail mail = this.notifyUsers(task, this.userTaskDefaultMailContent, 1);

        assertThat(mail.getReceivers()).isEqualTo(this.user.getEmail());
        assertThat(mail.getBody()).isEqualTo(this.defaultEmailBody);
    }

    @Test
    void testDelegateTask_WithCandidateUsers() throws Exception {
        final DelegateTask task = this.prepareDelegateTask(Map.of(
                "digitalwf_notification_send_assignee", "false",
                "digitalwf_notification_send_candidate_users", "true",
                "digitalwf_notification_send_candidate_groups", "false"
        ));
        when(task.getCandidates()).thenReturn(this.userCandidates);

        final Mail mail = this.notifyUsers(task, this.groupTaskDefaultMailContent, 1);

        assertThat(mail.getReceivers()).isEqualTo(this.candidate.getEmail());
        assertThat(mail.getBody()).isEqualTo(this.defaultEmailBody);
    }

    @Test
    void testDelegateTask_WithCandidateGroup() throws Exception {
        final DelegateTask task = this.prepareDelegateTask(Map.of(
                "digitalwf_notification_send_assignee", "false",
                "digitalwf_notification_send_candidate_users", "false",
                "digitalwf_notification_send_candidate_groups", "true"
        ));
        when(task.getCandidates()).thenReturn(this.groupCandidates);

        final Mail mail = this.notifyUsers(task, this.groupTaskDefaultMailContent, 1);

        assertThat(mail.getReceivers()).isEqualTo(this.candidate.getEmail());
        assertThat(mail.getBody()).isEqualTo(this.defaultEmailBody);
    }
//
//    /**
//     * Tests if a notification to the assignee and candidate users is sent out when notification is on.
//     * Process name should be read from ProcessDefinition.Key while ProcessDefinition.Name is not set
//     * and be written to Notification E-Mail.
//     */
////    @Test
//    void testDelegateTask_WithAssigneeAndCandidateUsers() throws Exception {
//        final DelegateTask task = this.prepareDelegateTask(Map.of(
//                "digitalwf_notification_send_assignee", "true",
//                "digitalwf_notification_send_candidate_users", "true",
//                "digitalwf_notification_send_candidate_groups", "false",
//                "app_task_assignee", this.user.getLhmObjectId()
//        ));
//        HashSet<IdentityLink> candidateSet = new HashSet<IdentityLink>();
//        IdentityLink identityLink = mock(IdentityLink.class);
//        when(identityLink.getUserId()).thenReturn(this.candidate.getLhmObjectId());
//        when(identityLink.getType()).thenReturn(IdentityLinkType.CANDIDATE);
//        candidateSet.add(identityLink);
//        when(task.getCandidates()).thenReturn(candidateSet);
//
//        ProcessDefinitionQuery query = QueryMocks.mockProcessDefinitionQuery(repositoryService).singleResult(ProcessDefinitionFake.builder()
//                .key("Testprozess-key").build());
//
//        // execute
//        this.userTaskNotificationListener.delegateTask(task);
//
//        verify(query, times(2)).processDefinitionId(task.getProcessDefinitionId());
//
//        final ArgumentCaptor<Mail> argument = ArgumentCaptor.forClass(Mail.class);
//        verify(this.digiwfEmailApi, times(2)).sendMailWithDefaultLogo(argument.capture());
//
//        final List<Mail> arguments = argument.getAllValues();
//        assertThat(arguments)
//                .extracting("receivers")
//                .isEqualTo(List.of(user.getEmail(), candidate.getEmail()));
//        // TODO proper test body
////        assertThat(arguments)
////                .extracting("body")
////                .isEqualTo(List.of("Sie haben eine Aufgabe in DigiWF (Testprozess-key).", "Sie haben eine Gruppenaufgabe in DigiWF (Testprozess-key)."));
//    }
//
//    /**
//     * Tests if a notification to the assignee and process name is read from ProcessDefinition.Name.
//     */
////    @Test
//    void testDelegateTask_WithAssignee_AndProcessName_ReadFromProcessDefinition() throws Exception {
//        final DelegateTask task = this.prepareDelegateTask(Map.of(
//                "digitalwf_notification_send_assignee", "true",
//                "digitalwf_notification_send_candidate_users", "false",
//                "digitalwf_notification_send_candidate_groups", "false",
//                "app_task_assignee", this.user.getLhmObjectId()
//        ));
//
//        HashSet<IdentityLink> candidateSet = new HashSet<IdentityLink>();
//        IdentityLink identityLink = mock(IdentityLink.class);
//        when(identityLink.getUserId()).thenReturn(this.candidate.getLhmObjectId());
//        when(identityLink.getType()).thenReturn(IdentityLinkType.CANDIDATE);
//        candidateSet.add(identityLink);
//        when(task.getCandidates()).thenReturn(candidateSet);
//
//        when(userService.getUser(this.candidate.getLhmObjectId())).thenReturn(candidate);
//
//        ProcessDefinitionQuery query = QueryMocks.mockProcessDefinitionQuery(repositoryService).singleResult(ProcessDefinitionFake.builder()
//                .key("Testprozess-name").build());
//
//        // execute
//        this.userTaskNotificationListener.delegateTask(task);
//
//        verify(query, times(1)).processDefinitionId(task.getProcessDefinitionId());
//
//        final ArgumentCaptor<Mail> argument = ArgumentCaptor.forClass(Mail.class);
//        verify(this.digiwfEmailApi, times(1)).sendMailWithDefaultLogo(argument.capture());
//
//        assertThat(argument.getValue().getReceivers()).isEqualTo(user.getEmail());
//        // TODO proper test body
////        assertThat(argument.getValue().getBody()).isEqualTo("Sie haben eine Aufgabe in DigiWF (Testprozess-name).");
//    }
//
//    /**
//     * Tests that no notification is sent out when notification is on but no assignee/candidates are defined.
//     */
////    @Test
//    void testDelegateTask_WithoutUsers() throws Exception {
//        final DelegateTask task = this.prepareDelegateTask(Map.of(
//                "digitalwf_notification_send_assignee", "true",
//                "digitalwf_notification_send_candidate_users", "true",
//                "digitalwf_notification_send_candidate_groups", "true"
//        ));
//
//        // execute
//        this.userTaskNotificationListener.delegateTask(task);
//
//        verify(this.digiwfEmailApi, times(0)).sendMailWithDefaultLogo(any(Mail.class));
//    }
//
//    /**
//     * Tests if a notification to the candidate groups is send out when notification is on.
//     */
////    @Test
//    void testDelegateTask_WithCandidateGroups() throws Exception {
//        final String groupName1 = "itm-km82";
//        final String groupName2 = "itm-km83";
//        final DelegateTask task = this.prepareDelegateTask(Map.of(
//                "digitalwf_notification_send_assignee", "false",
//                "digitalwf_notification_send_candidate_users", "false",
//                "digitalwf_notification_send_candidate_groups", "true",
//                "app_task_assignee", null
//        ));
//
//        HashSet<IdentityLink> candidateSet = new HashSet<IdentityLink>();
//        IdentityLink identityLink1 = mock(IdentityLink.class);
//        when(identityLink1.getGroupId()).thenReturn(groupName1);
//        when(identityLink1.getType()).thenReturn(IdentityLinkType.CANDIDATE);
//        candidateSet.add(identityLink1);
//        IdentityLink identityLink2 = mock(IdentityLink.class);
//        when(identityLink2.getGroupId()).thenReturn(groupName2);
//        when(identityLink2.getType()).thenReturn(IdentityLinkType.CANDIDATE);
//        candidateSet.add(identityLink2);
//        when(task.getCandidates()).thenReturn(candidateSet);
//
//        when(userService.getOuByShortName(groupName1)).thenReturn(Optional.of(this.user));
//        when(userService.getOuByShortName(groupName2)).thenReturn(Optional.of(this.candidate));
//
//        // execute
//        this.userTaskNotificationListener.delegateTask(task);
//
//        final ArgumentCaptor<Mail> argument = ArgumentCaptor.forClass(Mail.class);
//        verify(this.digiwfEmailApi, times(1)).sendMailWithDefaultLogo(argument.capture());
//
//        assertThat(argument.getValue().getReceivers()).contains(this.user.getEmail());
//        assertThat(argument.getValue().getReceivers()).contains(this.candidate.getEmail());
//    }
//
//    /**
//     * Tests if a notification to the assignee is send out with the default mail subject, body and bottom text.
//     */
////    @Test
//    void testDelegateTask_WithAssigneeAndDefaultSubjectBodyAndBottomText() throws Exception {
//        final DelegateTask task = this.prepareDelegateTask(Map.of(
//                "digitalwf_notification_send_assignee", "true",
//                "digitalwf_notification_send_candidate_users", "false",
//                "digitalwf_notification_send_candidate_groups", "false",
//                "app_task_assignee", this.user.getLhmObjectId()
//        ));
//        when(task.getCandidates()).thenReturn(Collections.<IdentityLink>emptySet());
//
//        // execute
//        this.userTaskNotificationListener.delegateTask(task);
//
//        // check if service is called with defined mail addresses
//        final ArgumentCaptor<Mail> argument = ArgumentCaptor.forClass(Mail.class);
//        verify(this.digiwfEmailApi, times(1)).sendMailWithDefaultLogo(argument.capture());
//
//        assertThat(argument.getValue().getReceivers()).isEqualTo(this.user.getEmail());
//        assertThat(argument.getValue().getSubject()).isEqualTo("Es liegt eine neue Aufgabe für Sie bereit");
//        // TODO proper test body
////        assertThat(argument.getValue().getBody()).isEqualTo("Sie haben eine Aufgabe in DigiWF.");
//    }
//
//    /**
//     * Tests if a notification to the assignee is send out with the customized mail subject, body and bottom text.
//     */
////    @Test
//    void testDelegateTask_WithAssigneeAndCustomizedSubjectBodyAndBottomText() throws Exception {
//        final DelegateTask task = this.prepareDelegateTask(Map.of(
//                "digitalwf_notification_send_assignee", "true",
//                "digitalwf_notification_send_candidate_users", "false",
//                "digitalwf_notification_send_candidate_groups", "false",
//                "app_task_assignee", this.user.getLhmObjectId(),
//                "mail_subject", "Neue Testaufgabe",
//                "mail_body", "Hier kommen Sie zu der neuen Testaufgabe.",
//                "mail_bottom_text", "Viele Grüße"
//        ));
//        when(task.getCandidates()).thenReturn(Collections.<IdentityLink>emptySet());
//
//        // execute
//        this.userTaskNotificationListener.delegateTask(task);
//
//        final ArgumentCaptor<Mail> argument = ArgumentCaptor.forClass(Mail.class);
//        verify(this.digiwfEmailApi, times(1)).sendMailWithDefaultLogo(argument.capture());
//
//        assertThat(argument.getValue().getReceivers()).isEqualTo(this.user.getEmail());
//        assertThat(argument.getValue().getSubject()).isEqualTo("Neue Testaufgabe");
//        // TODO proper test body
////        assertThat(argument.getValue().getBody()).contains("Hier kommen Sie zu der neuen Testaufgabe.");
////        assertThat(argument.getValue().getBody()).contains("Viele Grüße");
//    }
//
//    /**
//     * Tests if a notification to the candidate users and groups is send out with the default mail subject, body and bottom text.
//     */
////    @Test
//    void testDelegateTask_WithCandidateUsersAndCandidateGroupsAndDefaultSubjectBodyAndBottomText() throws Exception {
//        final String groupName = "itm-km82";
//        final DelegateTask task = this.prepareDelegateTask(Map.of(
//                "digitalwf_notification_send_assignee", "false",
//                "digitalwf_notification_send_candidate_users", "true",
//                "digitalwf_notification_send_candidate_groups", "true",
//                "app_task_assignee", this.user.getLhmObjectId()
//        ));
//        HashSet<IdentityLink> candidateSet = new HashSet<IdentityLink>();
//        IdentityLink identityLink1 = mock(IdentityLink.class);
//        when(identityLink1.getUserId()).thenReturn(this.user.getLhmObjectId());
//        when(identityLink1.getType()).thenReturn(IdentityLinkType.CANDIDATE);
//        candidateSet.add(identityLink1);
//        IdentityLink identityLink2 = mock(IdentityLink.class);
//        when(identityLink2.getGroupId()).thenReturn(groupName);
//        when(identityLink2.getType()).thenReturn(IdentityLinkType.CANDIDATE);
//        candidateSet.add(identityLink2);
//        when(task.getCandidates()).thenReturn(candidateSet);
//
//        when(userService.getOuByShortName(groupName)).thenReturn(Optional.of(this.candidate));
//
//        // execute
//        this.userTaskNotificationListener.delegateTask(task);
//
//        final ArgumentCaptor<Mail> argument = ArgumentCaptor.forClass(Mail.class);
//        verify(this.digiwfEmailApi, times(2)).sendMailWithDefaultLogo(argument.capture());
//
//        assertThat(argument.getValue().getReceivers()).isEqualTo(List.of(this.user.getEmail(), this.candidate.getEmail()));
//        // TODO proper test body
////        assertThat(argument.getValue().getSubject()).isEqualTo("Es liegt eine neue Gruppenaufgabe für Sie bereit");
////        assertThat(argument.getValue().getBody()).contains("Sie haben eine Gruppenaufgabe in DigiWF.");
//    }
//
//    /**
//     * Tests if a notification to the candidate users and groups is send out with the customized mail subject, body and bottom text.
//     */
////    @Test
//    void testDelegateTask_WithCandidateUsersAndCandidateGroupsAndCustomizedSubjectBodyAndBottomText() throws Exception {
//        final String groupName = "itm-km82";
//        final DelegateTask task = this.prepareDelegateTask(Map.of(
//                "digitalwf_notification_send_assignee", "false",
//                "digitalwf_notification_send_candidate_users", "true",
//                "digitalwf_notification_send_candidate_groups", "true",
//                "app_task_assignee", null,
//                "mail_subject", "Neue Testaufgabe",
//                "mail_body", "Hier kommen Sie zu der neuen Testaufgabe.",
//                "mail_bottom_text", "Viele Grüße"
//        ));
//        HashSet<IdentityLink> candidateSet = new HashSet<IdentityLink>();
//        IdentityLink identityLink1 = mock(IdentityLink.class);
//        when(identityLink1.getUserId()).thenReturn(this.user.getLhmObjectId());
//        when(identityLink1.getType()).thenReturn(IdentityLinkType.CANDIDATE);
//        candidateSet.add(identityLink1);
//        IdentityLink identityLink2 = mock(IdentityLink.class);
//        when(identityLink2.getGroupId()).thenReturn(groupName);
//        when(identityLink2.getType()).thenReturn(IdentityLinkType.CANDIDATE);
//        candidateSet.add(identityLink2);
//        when(task.getCandidates()).thenReturn(candidateSet);
//
//        when(userService.getOuByShortName(groupName)).thenReturn(Optional.of(this.candidate));
//
//        // execute
//        this.userTaskNotificationListener.delegateTask(task);
//
//        final ArgumentCaptor<Mail> argument = ArgumentCaptor.forClass(Mail.class);
//        verify(this.digiwfEmailApi, times(2)).sendMailWithDefaultLogo(argument.capture());
//
//        assertThat(argument.getValue().getReceivers()).isEqualTo(List.of(this.user.getEmail(), this.candidate.getEmail()));
//        assertThat(argument.getValue().getSubject()).isEqualTo("Neue Testaufgabe");
//        // TODO proper test body
////        assertThat(argument.getValue().getBody()).contains("Hier kommen Sie zu der neuen Testaufgabe.");
////        assertThat(argument.getValue().getBody()).contains("Viele Grüße");
//    }
}
