/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2021
 */
package de.muenchen.oss.digiwf.humantask.process.listener;

import de.muenchen.oss.digiwf.legacy.mailing.domain.model.MailTemplate;
import de.muenchen.oss.digiwf.legacy.mailing.domain.service.MailingService;
import de.muenchen.oss.digiwf.shared.properties.DigitalWFProperties;
import de.muenchen.oss.digiwf.legacy.user.domain.model.User;
import de.muenchen.oss.digiwf.legacy.user.domain.service.UserService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.task.IdentityLink;
import org.camunda.bpm.engine.task.IdentityLinkType;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Tests for UserTaskNotificationListener.
 *
 * @author martin.dietrich
 */
public class UserTaskNotificationListenerV02Test {

    /**
     * Tests if no notification is send out when all notification switches are off.
     */
    @Test
    public void testDelegateTask_WithNotificationOff() throws Exception {
        DelegateTask task = Mockito.mock(DelegateTask.class);
        Mockito.when(task.getEventName()).thenReturn("create");
        Mockito.when(task.getVariable("app_notification_send_assignee")).thenReturn("false");
        Mockito.when(task.getVariable("app_notification_send_candidate_users")).thenReturn("false");
        Mockito.when(task.getVariable("app_notification_send_candidate_groups")).thenReturn("false");
        Mockito.when(task.getVariable("app_task_assignee")).thenReturn("flash.gordon");
        DigitalWFProperties properties = Mockito.mock(DigitalWFProperties.class);
        UserService userService = Mockito.mock(UserService.class);
        MailingService mailingService = Mockito.mock(MailingService.class);
        RepositoryService repositoryService = Mockito.mock(RepositoryService.class);

        // execute
        new UserTaskNotificationListener(repositoryService, mailingService, userService, properties).delegateTask(task);

        Mockito.verify(mailingService, times(0)).sendMailTemplateWithLink(ArgumentMatchers.any(MailTemplate.class));
    }

    /**
     * Tests if a notification to the assignee is send out when notification is on.
     */
    @Test
    public void testDelegateTask_WithAssignee() throws Exception {
        final String username = "flash.gordon";
        DelegateTask task = Mockito.mock(DelegateTask.class);
        Mockito.when(task.getEventName()).thenReturn("create");
        Mockito.when(task.getVariable("app_notification_send_assignee")).thenReturn("true");
        Mockito.when(task.getVariable("app_notification_send_candidate_users")).thenReturn("false");
        Mockito.when(task.getVariable("app_notification_send_candidate_groups")).thenReturn("false");
        Mockito.when(task.getVariable("app_task_assignee")).thenReturn(username);
        when(task.getCandidates()).thenReturn(Collections.<IdentityLink>emptySet());

        DigitalWFProperties properties = Mockito.mock(DigitalWFProperties.class);
        UserService userService = Mockito.mock(UserService.class);
        User user = new User();
        user.setEmail(username + "@muenchen.de");
        when(userService.getUser(username)).thenReturn(user);
        MailingService mailingService = Mockito.mock(MailingService.class);
        RepositoryService repositoryService = Mockito.mock(RepositoryService.class);

        // execute
        new UserTaskNotificationListener(repositoryService, mailingService, userService, properties).delegateTask(task);

        ArgumentCaptor<MailTemplate> argument = ArgumentCaptor.forClass(MailTemplate.class);
        verify(mailingService).sendMailTemplateWithLink(argument.capture());
        assertEquals(user.getEmail(), argument.getValue().getReceivers());
    }

    /**
     * Tests if a notification to the assignee and candidate users is send out when notification is on.
     */
    @Test
    public void testDelegateTask_WithAssigneeAndCandidateUsers() throws Exception {
        final String username = "flash.gordon";
        final String candidateName = "dale.arden";
        DelegateTask task = Mockito.mock(DelegateTask.class);
        Mockito.when(task.getEventName()).thenReturn("create");
        Mockito.when(task.getVariable("app_notification_send_assignee")).thenReturn("true");
        Mockito.when(task.getVariable("app_notification_send_candidate_users")).thenReturn("true");
        Mockito.when(task.getVariable("app_notification_send_candidate_groups")).thenReturn("false");
        Mockito.when(task.getVariable("app_task_assignee")).thenReturn(username);
        HashSet<IdentityLink> candidateSet = new HashSet<IdentityLink>();
        IdentityLink identityLink = Mockito.mock(IdentityLink.class);
        when(identityLink.getUserId()).thenReturn(candidateName);
        when(identityLink.getType()).thenReturn(IdentityLinkType.CANDIDATE);
        candidateSet.add(identityLink);
        when(task.getCandidates()).thenReturn(candidateSet);

        DigitalWFProperties properties = Mockito.mock(DigitalWFProperties.class);
        UserService userService = Mockito.mock(UserService.class);
        User user = new User();
        user.setEmail(username + "@muenchen.de");
        when(userService.getUser(username)).thenReturn(user);
        User candidate1 = new User();
        candidate1.setEmail(candidateName + "@muenchen.de");
        when(userService.getUser(candidateName)).thenReturn(candidate1);
        MailingService mailingService = Mockito.mock(MailingService.class);
        RepositoryService repositoryService = Mockito.mock(RepositoryService.class);

        // execute
        new UserTaskNotificationListener(repositoryService, mailingService, userService, properties).delegateTask(task);

        ArgumentCaptor<MailTemplate> argument = ArgumentCaptor.forClass(MailTemplate.class);
        verify(mailingService, times(2)).sendMailTemplateWithLink(argument.capture());
        List<MailTemplate> arguments = argument.getAllValues();
        assertTrue(arguments.stream().anyMatch(a -> a.getReceivers().contains(user.getEmail())));
        assertTrue(arguments.stream().anyMatch(a -> a.getReceivers().contains(candidate1.getEmail())));
    }

    /**
     * Tests if a notification to the candidate users is send out when notification is on.
     */
    @Test
    public void testDelegateTask_WithCandidateUsers() throws Exception {
        final String candidateName1 = "dale.arden";
        final String candidateName2 = "flash.gordon";
        DelegateTask task = Mockito.mock(DelegateTask.class);
        Mockito.when(task.getEventName()).thenReturn("create");
        Mockito.when(task.getVariable("app_notification_send_assignee")).thenReturn("false");
        Mockito.when(task.getVariable("app_notification_send_candidate_users")).thenReturn("true");
        Mockito.when(task.getVariable("app_notification_send_candidate_groups")).thenReturn("false");
        Mockito.when(task.getVariable("app_task_assignee")).thenReturn(null);
        HashSet<IdentityLink> candidateSet = new HashSet<IdentityLink>();
        IdentityLink identityLink1 = Mockito.mock(IdentityLink.class);
        when(identityLink1.getUserId()).thenReturn(candidateName1);
        when(identityLink1.getType()).thenReturn(IdentityLinkType.CANDIDATE);
        candidateSet.add(identityLink1);
        IdentityLink identityLink2 = Mockito.mock(IdentityLink.class);
        when(identityLink2.getUserId()).thenReturn(candidateName2);
        when(identityLink2.getType()).thenReturn(IdentityLinkType.CANDIDATE);
        candidateSet.add(identityLink2);
        when(task.getCandidates()).thenReturn(candidateSet);

        DigitalWFProperties properties = Mockito.mock(DigitalWFProperties.class);
        UserService userService = Mockito.mock(UserService.class);
        User user1 = new User();
        user1.setEmail(candidateName1 + "@muenchen.de");
        when(userService.getUser(candidateName1)).thenReturn(user1);
        User user2 = new User();
        user2.setEmail(candidateName2 + "@muenchen.de");
        when(userService.getUser(candidateName2)).thenReturn(user2);
        MailingService mailingService = Mockito.mock(MailingService.class);
        RepositoryService repositoryService = Mockito.mock(RepositoryService.class);

        // execute
        new UserTaskNotificationListener(repositoryService, mailingService, userService, properties).delegateTask(task);

        // check if service is called with defined mail addresses
        ArgumentCaptor<MailTemplate> argument = ArgumentCaptor.forClass(MailTemplate.class);
        verify(mailingService).sendMailTemplateWithLink(argument.capture());
        assertTrue(argument.getValue().getReceivers().contains(user1.getEmail()));
        assertTrue(argument.getValue().getReceivers().contains(user2.getEmail()));
    }

    /**
     * Tests that no notification is send out when notification is on but no assignee/candidates are defined.
     */
    @Test
    public void testDelegateTask_WithoutUsers() throws Exception {
        DelegateTask task = Mockito.mock(DelegateTask.class);
        Mockito.when(task.getEventName()).thenReturn("create");
        Mockito.when(task.getVariable("app_notification_send_assignee")).thenReturn("true");
        Mockito.when(task.getVariable("app_notification_send_candidate_users")).thenReturn("true");
        Mockito.when(task.getVariable("app_notification_send_candidate_groups")).thenReturn("true");
        DigitalWFProperties properties = Mockito.mock(DigitalWFProperties.class);
        UserService userService = Mockito.mock(UserService.class);
        MailingService mailingService = Mockito.mock(MailingService.class);
        RepositoryService repositoryService = Mockito.mock(RepositoryService.class);

        // execute
        new UserTaskNotificationListener(repositoryService, mailingService, userService, properties).delegateTask(task);

        Mockito.verify(mailingService, times(0)).sendMailTemplateWithLink(ArgumentMatchers.any(MailTemplate.class));
    }

    /**
     * Tests if a notification to the candidate groups is send out when notification is on.
     */
    @Test
    public void testDelegateTask_WithCandidateGroups() throws Exception {
        final String groupName1 = "itm-km82";
        final String groupName2 = "itm-km83";
        DelegateTask task = Mockito.mock(DelegateTask.class);
        Mockito.when(task.getEventName()).thenReturn("create");
        Mockito.when(task.getVariable("app_notification_send_assignee")).thenReturn("false");
        Mockito.when(task.getVariable("app_notification_send_candidate_users")).thenReturn("false");
        Mockito.when(task.getVariable("app_notification_send_candidate_groups")).thenReturn("true");
        Mockito.when(task.getVariable("app_task_assignee")).thenReturn(null);
        HashSet<IdentityLink> candidateSet = new HashSet<IdentityLink>();
        IdentityLink identityLink1 = Mockito.mock(IdentityLink.class);
        when(identityLink1.getGroupId()).thenReturn(groupName1);
        when(identityLink1.getType()).thenReturn(IdentityLinkType.CANDIDATE);
        candidateSet.add(identityLink1);
        IdentityLink identityLink2 = Mockito.mock(IdentityLink.class);
        when(identityLink2.getGroupId()).thenReturn(groupName2);
        when(identityLink2.getType()).thenReturn(IdentityLinkType.CANDIDATE);
        candidateSet.add(identityLink2);
        when(task.getCandidates()).thenReturn(candidateSet);

        DigitalWFProperties properties = Mockito.mock(DigitalWFProperties.class);
        UserService userService = Mockito.mock(UserService.class);
        User user1 = new User();
        user1.setEmail(groupName1 + "@muenchen.de");
        when(userService.getOuByShortName(groupName1)).thenReturn(Optional.of(user1));
        User user2 = new User();
        user2.setEmail(groupName2 + "@muenchen.de");
        when(userService.getOuByShortName(groupName2)).thenReturn(Optional.of(user2));
        MailingService mailingService = Mockito.mock(MailingService.class);
        RepositoryService repositoryService = Mockito.mock(RepositoryService.class);

        // execute
        new UserTaskNotificationListener(repositoryService, mailingService, userService, properties).delegateTask(task);

        ArgumentCaptor<MailTemplate> argument = ArgumentCaptor.forClass(MailTemplate.class);
        verify(mailingService).sendMailTemplateWithLink(argument.capture());
        assertTrue(argument.getValue().getReceivers().contains(user1.getEmail()));
        assertTrue(argument.getValue().getReceivers().contains(user2.getEmail()));
    }

    /**
     * Tests if a notification to the assignee and candidate groups is send out when notification is on.
     */
    @Test
    public void testDelegateTask_WithCandidateGroupsAndAssignee() throws Exception {
        final String userName1 = "flash.gordon";
        final String groupName1 = "itm-km82";
        final String groupName2 = "itm-km83";
        DelegateTask task = Mockito.mock(DelegateTask.class);
        Mockito.when(task.getEventName()).thenReturn("create");
//        Mockito.when(task.getVariable("app_notification_send_assignee")).thenReturn("true");
        Mockito.when(task.getVariable("app_notification_send_candidate_users")).thenReturn("false");
//        Mockito.when(task.getVariable("app_notification_send_candidate_groups")).thenReturn("true");
        Mockito.when(task.getVariable("app_task_assignee")).thenReturn(userName1);
        HashSet<IdentityLink> candidateSet = new HashSet<IdentityLink>();
        IdentityLink identityLink1 = Mockito.mock(IdentityLink.class);
        when(identityLink1.getGroupId()).thenReturn(groupName1);
        when(identityLink1.getType()).thenReturn(IdentityLinkType.CANDIDATE);
        candidateSet.add(identityLink1);
        IdentityLink identityLink2 = Mockito.mock(IdentityLink.class);
        when(identityLink2.getGroupId()).thenReturn(groupName2);
        when(identityLink2.getType()).thenReturn(IdentityLinkType.CANDIDATE);
        candidateSet.add(identityLink2);
        when(task.getCandidates()).thenReturn(candidateSet);

        DigitalWFProperties properties = Mockito.mock(DigitalWFProperties.class);
        UserService userService = Mockito.mock(UserService.class);
        User user0 = new User();
        user0.setEmail(userName1 + "@muenchen.de");
        when(userService.getUser(userName1)).thenReturn(user0);
        User user1 = new User();
        user1.setEmail(groupName1 + "@muenchen.de");
        when(userService.getOuByShortName(groupName1)).thenReturn(Optional.of(user1));
        User user2 = new User();
        user2.setEmail(groupName2 + "@muenchen.de");
        when(userService.getOuByShortName(groupName2)).thenReturn(Optional.of(user2));
        MailingService mailingService = Mockito.mock(MailingService.class);
        RepositoryService repositoryService = Mockito.mock(RepositoryService.class);

        // execute
        new UserTaskNotificationListener(repositoryService, mailingService, userService, properties).delegateTask(task);

        ArgumentCaptor<MailTemplate> argument = ArgumentCaptor.forClass(MailTemplate.class);
        verify(mailingService, times(2)).sendMailTemplateWithLink(argument.capture());
        List<MailTemplate> arguments = argument.getAllValues();
        assertTrue(arguments.stream().anyMatch(a -> a.getReceivers().contains(user0.getEmail())));
        assertTrue(arguments.stream().anyMatch(a -> a.getReceivers().contains(user1.getEmail())));
        assertTrue(arguments.stream().anyMatch(a -> a.getReceivers().contains(user2.getEmail())));
    }

    /**
     * Tests if a notification to the candidate users and groups is send out when notification is on.
     */
    @Test
    public void testDelegateTask_WithCandidateUsersAndCandidateGroups() throws Exception {
        final String userName1 = "flash.gordon";
        final String groupName1 = "itm-km82";
        final String groupName2 = "itm-km83";
        DelegateTask task = Mockito.mock(DelegateTask.class);
        Mockito.when(task.getEventName()).thenReturn("create");
//        Mockito.when(task.getVariable("app_notification_send_assignee")).thenReturn("false");
        Mockito.when(task.getVariable("app_notification_send_candidate_users")).thenReturn("true");
//        Mockito.when(task.getVariable("app_notification_send_candidate_groups")).thenReturn("true");
        Mockito.when(task.getVariable("app_task_assignee")).thenReturn(null);
        HashSet<IdentityLink> candidateSet = new HashSet<IdentityLink>();
        IdentityLink identityLink1 = Mockito.mock(IdentityLink.class);
        when(identityLink1.getGroupId()).thenReturn(groupName1);
        when(identityLink1.getType()).thenReturn(IdentityLinkType.CANDIDATE);
        candidateSet.add(identityLink1);
        IdentityLink identityLink2 = Mockito.mock(IdentityLink.class);
        when(identityLink2.getGroupId()).thenReturn(groupName2);
        when(identityLink2.getType()).thenReturn(IdentityLinkType.CANDIDATE);
        candidateSet.add(identityLink2);
        IdentityLink identityLink3 = Mockito.mock(IdentityLink.class);
        when(identityLink3.getUserId()).thenReturn(userName1);
        when(identityLink3.getType()).thenReturn(IdentityLinkType.CANDIDATE);
        candidateSet.add(identityLink3);
        when(task.getCandidates()).thenReturn(candidateSet);

        DigitalWFProperties properties = Mockito.mock(DigitalWFProperties.class);
        UserService userService = Mockito.mock(UserService.class);
        User user0 = new User();
        user0.setEmail(userName1 + "@muenchen.de");
        when(userService.getUser(userName1)).thenReturn(user0);
        User user1 = new User();
        user1.setEmail(groupName1 + "@muenchen.de");
        when(userService.getOuByShortName(groupName1)).thenReturn(Optional.of(user1));
        User user2 = new User();
        user2.setEmail(groupName2 + "@muenchen.de");
        when(userService.getOuByShortName(groupName2)).thenReturn(Optional.of(user2));
        MailingService mailingService = Mockito.mock(MailingService.class);
        RepositoryService repositoryService = Mockito.mock(RepositoryService.class);

        // execute
        new UserTaskNotificationListener(repositoryService, mailingService, userService, properties).delegateTask(task);

        ArgumentCaptor<MailTemplate> argument = ArgumentCaptor.forClass(MailTemplate.class);
        verify(mailingService, times(2)).sendMailTemplateWithLink(argument.capture());
        List<MailTemplate> arguments = argument.getAllValues();
        assertTrue(arguments.stream().anyMatch(a -> a.getReceivers().contains(user0.getEmail())));
        assertTrue(arguments.stream().anyMatch(a -> a.getReceivers().contains(user1.getEmail())));
        assertTrue(arguments.stream().anyMatch(a -> a.getReceivers().contains(user2.getEmail())));
    }

    /**
     * Tests if a notification to the assignee is send out with the default mail subject, body and bottom text.
     */
    @Test
    public void testDelegateTask_WithAssigneeAndDefaultSubjectBodyAndBottomText() throws Exception {
        final String username = "flash.gordon";
        DelegateTask task = Mockito.mock(DelegateTask.class);
        Mockito.when(task.getEventName()).thenReturn("create");
        Mockito.when(task.getVariable("app_notification_send_assignee")).thenReturn("true");
        Mockito.when(task.getVariable("app_notification_send_candidate_users")).thenReturn("false");
        Mockito.when(task.getVariable("app_notification_send_candidate_groups")).thenReturn("false");
        Mockito.when(task.getVariable("app_task_assignee")).thenReturn(username);
        when(task.getCandidates()).thenReturn(Collections.<IdentityLink>emptySet());

        DigitalWFProperties properties = Mockito.mock(DigitalWFProperties.class);
        UserService userService = Mockito.mock(UserService.class);
        User user = new User();
        user.setEmail(username + "@muenchen.de");
        when(userService.getUser(username)).thenReturn(user);
        MailingService mailingService = Mockito.mock(MailingService.class);
        RepositoryService repositoryService = Mockito.mock(RepositoryService.class);

        // execute
        new UserTaskNotificationListener(repositoryService, mailingService, userService, properties).delegateTask(task);

        ArgumentCaptor<MailTemplate> argument = ArgumentCaptor.forClass(MailTemplate.class);
        verify(mailingService).sendMailTemplateWithLink(argument.capture());
        assertEquals(user.getEmail(), argument.getValue().getReceivers());
        assertThat(argument.getValue().getSubject()).isEqualTo("Es liegt eine neue Aufgabe für Sie bereit");
        assertThat(argument.getValue().getBody()).isEqualTo("Sie haben eine Aufgabe in DigiWF.");
        assertThat(argument.getValue().getBottomText()).isBlank();
    }

    /**
     * Tests if a notification to the assignee is send out with the customized mail subject, body and bottom text.
     */
    @Test
    public void testDelegateTask_WithAssigneeAndCustomizedSubjectBodyAndBottomText() throws Exception {
        final String username = "flash.gordon";
        DelegateTask task = Mockito.mock(DelegateTask.class);
        Mockito.when(task.getEventName()).thenReturn("create");
        Mockito.when(task.getVariable("app_notification_send_assignee")).thenReturn("true");
        Mockito.when(task.getVariable("app_notification_send_candidate_users")).thenReturn("false");
        Mockito.when(task.getVariable("app_notification_send_candidate_groups")).thenReturn("false");
        Mockito.when(task.getVariable("app_task_assignee")).thenReturn(username);
        Mockito.when(task.getVariable("mail_subject")).thenReturn("Neue Testaufgabe");
        Mockito.when(task.getVariable("mail_body")).thenReturn("Hier kommen Sie zu der neuen Testaufgabe.");
        Mockito.when(task.getVariable("mail_bottom_text")).thenReturn("Viele Grüße");
        when(task.getCandidates()).thenReturn(Collections.<IdentityLink>emptySet());

        DigitalWFProperties properties = Mockito.mock(DigitalWFProperties.class);
        UserService userService = Mockito.mock(UserService.class);
        User user = new User();
        user.setEmail(username + "@muenchen.de");
        when(userService.getUser(username)).thenReturn(user);
        MailingService mailingService = Mockito.mock(MailingService.class);
        RepositoryService repositoryService = Mockito.mock(RepositoryService.class);

        // execute
        new UserTaskNotificationListener(repositoryService, mailingService, userService, properties).delegateTask(task);

        ArgumentCaptor<MailTemplate> argument = ArgumentCaptor.forClass(MailTemplate.class);
        verify(mailingService).sendMailTemplateWithLink(argument.capture());
        assertEquals(user.getEmail(), argument.getValue().getReceivers());
        assertThat(argument.getValue().getSubject()).isEqualTo("Neue Testaufgabe");
        assertThat(argument.getValue().getBody()).isEqualTo("Hier kommen Sie zu der neuen Testaufgabe.");
        assertThat(argument.getValue().getBottomText()).isEqualTo("Viele Grüße");
    }

    /**
     * Tests if a notification to the candidate users and groups is send out with the default mail subject, body and bottom text.
     */
    @Test
    public void testDelegateTask_WithCandidateUsersAndCandidateGroupsAndDefaultSubjectBodyAndBottomText() throws Exception {
        final String candidateName = "dale.arden";
        final String groupName = "itm-km82";
        DelegateTask task = Mockito.mock(DelegateTask.class);
        Mockito.when(task.getEventName()).thenReturn("create");
        Mockito.when(task.getVariable("app_notification_send_assignee")).thenReturn("false");
        Mockito.when(task.getVariable("app_notification_send_candidate_users")).thenReturn("true");
        Mockito.when(task.getVariable("app_notification_send_candidate_groups")).thenReturn("true");
        Mockito.when(task.getVariable("app_task_assignee")).thenReturn(null);
        HashSet<IdentityLink> candidateSet = new HashSet<IdentityLink>();
        IdentityLink identityLink1 = Mockito.mock(IdentityLink.class);
        when(identityLink1.getUserId()).thenReturn(candidateName);
        when(identityLink1.getType()).thenReturn(IdentityLinkType.CANDIDATE);
        candidateSet.add(identityLink1);
        IdentityLink identityLink2 = Mockito.mock(IdentityLink.class);
        when(identityLink2.getGroupId()).thenReturn(groupName);
        when(identityLink2.getType()).thenReturn(IdentityLinkType.CANDIDATE);
        candidateSet.add(identityLink2);
        when(task.getCandidates()).thenReturn(candidateSet);

        DigitalWFProperties properties = Mockito.mock(DigitalWFProperties.class);
        UserService userService = Mockito.mock(UserService.class);
        User user1 = new User();
        user1.setEmail(candidateName + "@muenchen.de");
        when(userService.getUser(candidateName)).thenReturn(user1);
        User user2 = new User();
        user2.setEmail(groupName + "@muenchen.de");
        when(userService.getOuByShortName(groupName)).thenReturn(Optional.of(user2));
        MailingService mailingService = Mockito.mock(MailingService.class);
        RepositoryService repositoryService = Mockito.mock(RepositoryService.class);

        // execute
        new UserTaskNotificationListener(repositoryService, mailingService, userService, properties).delegateTask(task);

        // check if service is called with defined mail addresses and if the right mail subject, body and bottom text are set
        ArgumentCaptor<MailTemplate> argument = ArgumentCaptor.forClass(MailTemplate.class);
        verify(mailingService, times(2)).sendMailTemplateWithLink(argument.capture());
        List<MailTemplate> arguments = argument.getAllValues();
        assertTrue(arguments.stream().anyMatch(a -> a.getReceivers().contains(user1.getEmail())));
        assertTrue(arguments.stream().anyMatch(a -> a.getReceivers().contains(user2.getEmail())));
        assertTrue(arguments.stream().allMatch(a -> a.getSubject().contains("Es liegt eine neue Gruppenaufgabe für Sie bereit")));
        assertTrue(arguments.stream().allMatch(a -> a.getBody().contains("Sie haben eine Gruppenaufgabe in DigiWF.")));
        assertTrue(arguments.stream().allMatch(a -> a.getBottomText().isBlank()));
    }

    /**
     * Tests if a notification to the candidate users and groups is send out with the customized mail subject, body and bottom text.
     */
    @Test
    public void testDelegateTask_WithCandidateUsersAndCandidateGroupsAndCustomizedSubjectBodyAndBottomText() throws Exception {
        final String candidateName = "dale.arden";
        final String groupName = "itm-km82";
        DelegateTask task = Mockito.mock(DelegateTask.class);
        Mockito.when(task.getEventName()).thenReturn("create");
        Mockito.when(task.getVariable("app_notification_send_assignee")).thenReturn("false");
        Mockito.when(task.getVariable("app_notification_send_candidate_users")).thenReturn("true");
        Mockito.when(task.getVariable("app_notification_send_candidate_groups")).thenReturn("true");
        Mockito.when(task.getVariable("app_task_assignee")).thenReturn(null);
        Mockito.when(task.getVariable("mail_subject")).thenReturn("Neue Testaufgabe");
        Mockito.when(task.getVariable("mail_body")).thenReturn("Hier kommen Sie zu der neuen Testaufgabe.");
        Mockito.when(task.getVariable("mail_bottom_text")).thenReturn("Viele Grüße");
        HashSet<IdentityLink> candidateSet = new HashSet<IdentityLink>();
        IdentityLink identityLink1 = Mockito.mock(IdentityLink.class);
        when(identityLink1.getUserId()).thenReturn(candidateName);
        when(identityLink1.getType()).thenReturn(IdentityLinkType.CANDIDATE);
        candidateSet.add(identityLink1);
        IdentityLink identityLink2 = Mockito.mock(IdentityLink.class);
        when(identityLink2.getGroupId()).thenReturn(groupName);
        when(identityLink2.getType()).thenReturn(IdentityLinkType.CANDIDATE);
        candidateSet.add(identityLink2);
        when(task.getCandidates()).thenReturn(candidateSet);

        DigitalWFProperties properties = Mockito.mock(DigitalWFProperties.class);
        UserService userService = Mockito.mock(UserService.class);
        User user1 = new User();
        user1.setEmail(candidateName + "@muenchen.de");
        when(userService.getUser(candidateName)).thenReturn(user1);
        User user2 = new User();
        user2.setEmail(groupName + "@muenchen.de");
        when(userService.getOuByShortName(groupName)).thenReturn(Optional.of(user2));
        MailingService mailingService = Mockito.mock(MailingService.class);
        RepositoryService repositoryService = Mockito.mock(RepositoryService.class);

        // execute
        new UserTaskNotificationListener(repositoryService, mailingService, userService, properties).delegateTask(task);

        // check if service is called with defined mail addresses and if the right mail subject, body and bottom text are set
        ArgumentCaptor<MailTemplate> argument = ArgumentCaptor.forClass(MailTemplate.class);
        verify(mailingService, times(2)).sendMailTemplateWithLink(argument.capture());
        List<MailTemplate> arguments = argument.getAllValues();
        assertTrue(arguments.stream().anyMatch(a -> a.getReceivers().contains(user1.getEmail())));
        assertTrue(arguments.stream().anyMatch(a -> a.getReceivers().contains(user2.getEmail())));
        assertTrue(arguments.stream().allMatch(a -> a.getSubject().contains("Neue Testaufgabe")));
        assertTrue(arguments.stream().allMatch(a -> a.getBody().contains("Hier kommen Sie zu der neuen Testaufgabe.")));
        assertTrue(arguments.stream().allMatch(a -> a.getBottomText().contains("Viele Grüße")));
    }

}
