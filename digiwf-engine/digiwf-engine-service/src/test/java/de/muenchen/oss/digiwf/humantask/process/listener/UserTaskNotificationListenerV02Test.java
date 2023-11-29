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
class UserTaskNotificationListenerV02Test extends BaseUserTaskNotificationListenerTest {

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
                "app_notification_send_assignee", "false",
                "app_notification_send_candidate_users", "false",
                "app_notification_send_candidate_groups", "false",
                "app_task_assignee", this.user.getLhmObjectId()
        ));
        this.notifyUsers(task, null, 0);
    }

    @Test
    void testDelegateTask_DoesNotSendEmailIfAssigneeIsNull() throws Exception {
        final DelegateTask task = this.prepareDelegateTask(Map.of(
                "app_notification_send_assignee", "true",
                "app_notification_send_candidate_users", "false",
                "app_notification_send_candidate_groups", "false"
        ));
        this.notifyUsers(task, null, 0);
    }

    @Test
    void testDelegateTask_WithAssignee() throws Exception {
        final DelegateTask task = this.prepareDelegateTask(Map.of(
                "app_notification_send_assignee", "true",
                "app_notification_send_candidate_users", "false",
                "app_notification_send_candidate_groups", "false",
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
                "app_notification_send_assignee", "false",
                "app_notification_send_candidate_users", "true",
                "app_notification_send_candidate_groups", "false"
        ));
        when(task.getCandidates()).thenReturn(this.userCandidates);

        final Mail mail = this.notifyUsers(task, this.groupTaskDefaultMailContent, 1);

        assertThat(mail.getReceivers()).isEqualTo(this.candidate.getEmail());
        assertThat(mail.getBody()).isEqualTo(this.defaultEmailBody);
    }

    @Test
    void testDelegateTask_WithCandidateGroup() throws Exception {
        final DelegateTask task = this.prepareDelegateTask(Map.of(
                "app_notification_send_assignee", "false",
                "app_notification_send_candidate_users", "false",
                "app_notification_send_candidate_groups", "true"
        ));
        when(task.getCandidates()).thenReturn(this.groupCandidates);

        final Mail mail = this.notifyUsers(task, this.groupTaskDefaultMailContent, 1);

        assertThat(mail.getReceivers()).isEqualTo(this.candidate.getEmail());
        assertThat(mail.getBody()).isEqualTo(this.defaultEmailBody);
    }


    @Test
    void testDelegateTask_WithCustomMailContentForUserTaskNotifications() throws Exception {
        final DelegateTask task = this.prepareDelegateTask(Map.of(
                "app_notification_send_assignee", "true",
                "app_notification_send_candidate_users", "false",
                "app_notification_send_candidate_groups", "false",
                "app_task_assignee", this.user.getLhmObjectId(),
                "mail_body", "Email Body",
                "mail_bottom_text", "Some Bottom Text",
                "mail_subject", "Email Subject"
        ));
        when(task.getCandidates()).thenReturn(Collections.<IdentityLink>emptySet());
        final Map<String, String> customMailContent = Map.of(
                "%%body_top%%", "Email Body",
                "%%body_bottom%%", "Some Bottom Text",
                "%%button_link%%", this.frontendUrl + "/#/task/" + this.taskId,
                "%%button_text%%", "Aufgabe öffnen",
                "%%footer%%", "DigiWF 2.0<br>IT-Referat der Stadt München"
        );

        final Mail mail = this.notifyUsers(task, customMailContent, 1);

        assertThat(mail.getReceivers()).isEqualTo(this.user.getEmail());
        assertThat(mail.getBody()).isEqualTo(this.defaultEmailBody);
        assertThat(mail.getSubject()).isEqualTo("Email Subject");
    }

    @Test
    void testDelegateTask_WithCustomMailContentForGroupTaskNotifications() throws Exception {
        final DelegateTask task = this.prepareDelegateTask(Map.of(
                "app_notification_send_assignee", "false",
                "app_notification_send_candidate_users", "true",
                "app_notification_send_candidate_groups", "false",
                "mail_body", "Email Body",
                "mail_bottom_text", "Some Bottom Text",
                "mail_subject", "Email Subject"
        ));
        when(task.getCandidates()).thenReturn(this.userCandidates);
        final Map<String, String> customMailContent = Map.of(
                "%%body_top%%", "Email Body",
                "%%body_bottom%%", "Some Bottom Text",
                "%%button_link%%", this.frontendUrl + "/#/opengrouptask/" + this.taskId,
                "%%button_text%%", "Gruppenaufgabe öffnen",
                "%%footer%%", "DigiWF 2.0<br>IT-Referat der Stadt München"
        );

        final Mail mail = this.notifyUsers(task, customMailContent, 1);

        assertThat(mail.getReceivers()).isEqualTo(this.candidate.getEmail());
        assertThat(mail.getBody()).isEqualTo(this.defaultEmailBody);
        assertThat(mail.getSubject()).isEqualTo("Email Subject");
    }

}
