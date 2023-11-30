/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.humantask.process.listener;

import de.muenchen.oss.digiwf.email.api.DigiwfEmailApi;
import de.muenchen.oss.digiwf.email.model.Mail;
import de.muenchen.oss.digiwf.humantask.process.ProcessTaskConstants;
import de.muenchen.oss.digiwf.legacy.user.domain.model.User;
import de.muenchen.oss.digiwf.legacy.user.domain.service.UserService;
import de.muenchen.oss.digiwf.shared.properties.DigitalWFProperties;
import de.muenchen.oss.digiwf.task.TaskVariables;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.task.IdentityLink;
import org.camunda.bpm.engine.task.IdentityLinkType;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.muenchen.oss.digiwf.task.TaskVariables.TASK_ASSIGNEE;
import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;

/**
 * Notifies the associated users during the creation of a user task.
 *
 * @author externer.dl.horn
 */
@Component
@Profile("!no-mail")
@RequiredArgsConstructor
@Slf4j
public class UserTaskNotificationListener {

    private final RepositoryService repositoryService;
    private final DigiwfEmailApi digiwfEmailApi;
    private final UserService userService;
    private final DigitalWFProperties properties;

    @Deprecated // use other switches instead
    private static final VariableFactory<String> NOTIFICATION_SEND = stringVariable("digitalwf_notification_send");

    @Deprecated
    private static final VariableFactory<String> NOTIFICATION_SEND_ASSIGNEE = stringVariable("digitalwf_notification_send_assignee");
    @Deprecated
    private static final VariableFactory<String> NOTIFICATION_SEND_CANDIDATE_USERS = stringVariable("digitalwf_notification_send_candidate_users");
    @Deprecated
    private static final VariableFactory<String> NOTIFICATION_SEND_CANDIDATE_GROUPS = stringVariable("digitalwf_notification_send_candidate_groups");

    @EventListener
    public void delegateTask(final DelegateTask delegateTask) throws Exception {
        if (delegateTask.getEventName().equals("create")) {

            log.debug("Notification for created task: {}", delegateTask.getName());

            val notify = NOTIFICATION_SEND.from(delegateTask).getOptional();
            if (notify.isPresent()) {
                if ("true".equals(notify.get())) {
                    this.notifyAssignee(delegateTask);
                }
                return; // ignore other switches when deprecated feature is on
            }

            val notifyAssignee = NOTIFICATION_SEND_ASSIGNEE.from(delegateTask).getOptional();
            val notifyAssigneeV02 = ProcessTaskConstants.APP_NOTIFICATION_SEND_ASSIGNEE.from(delegateTask).getOptional();
            if ((!notifyAssignee.isPresent() || "true".equals(notifyAssignee.get()))
                    && (!notifyAssigneeV02.isPresent() || "true".equals(notifyAssigneeV02.get()))) {
                this.notifyAssignee(delegateTask);
            }

            val notifyCandidateUsers = NOTIFICATION_SEND_CANDIDATE_USERS.from(delegateTask).getOptional();
            val notifyCandidateUsersV02 = ProcessTaskConstants.APP_NOTIFICATION_SEND_CANDIDATE_USERS.from(delegateTask).getOptional();
            if ((!notifyCandidateUsers.isPresent() || "true".equals(notifyCandidateUsers.get()))
                    && (!notifyCandidateUsersV02.isPresent() || "true".equals(notifyCandidateUsersV02.get()))) {
                this.notifyCandidateUsers(delegateTask);
            }

            val notifyCandidateGroups = NOTIFICATION_SEND_CANDIDATE_GROUPS.from(delegateTask).getOptional();
            val notifyCandidateGroupsV02 = ProcessTaskConstants.APP_NOTIFICATION_SEND_CANDIDATE_GROUPS.from(delegateTask).getOptional();
            if ((!notifyCandidateGroups.isPresent() || "true".equals(notifyCandidateGroups.get()))
                    && (!notifyCandidateGroupsV02.isPresent() || "true".equals(notifyCandidateGroupsV02.get()))) {
                this.notifyCandidateGroups(delegateTask);
            }
        }
    }

    private void notifyAssignee(final DelegateTask delegateTask) throws Exception {
        final String assignedUserId = TASK_ASSIGNEE.from(delegateTask).getOptional().orElseGet(delegateTask::getAssignee);
        if (StringUtils.isBlank(assignedUserId)) {
            return;
        }
        try {
            String processName = this.getProcessName(delegateTask.getProcessDefinitionId());
            val address = this.getMailAddress(assignedUserId);

            final String subject = TaskVariables.MAIL_SUBJECT.from(delegateTask).getOrDefault("Es liegt eine neue Aufgabe für Sie bereit");
            final String body = TaskVariables.MAIL_BODY.from(delegateTask).getOrDefault(this.getBodyAssignee(processName));
            final String bottomText = TaskVariables.MAIL_BOTTOM_TEXT.from(delegateTask).getOrDefault("");

            final Map<String, String> emailContent = Map.of(
                    "%%body_top%%", body,
                    "%%body_bottom%%", bottomText,
                    "%%button_link%%", this.properties.getFrontendUrl() + "/#/task/" + delegateTask.getId(),
                    "%%button_text%%", "Aufgabe öffnen",
                    "%%footer%%", "DigiWF 2.0<br>IT-Referat der Stadt München"
            );
            final String templatePath = "bausteine/mail/templatewithlink/mail-template.tpl";
            final String emailBody = this.digiwfEmailApi.getEmailBodyFromTemplate(templatePath, emailContent);

            final Mail mail = Mail.builder()
                    .receivers(address)
                    .subject(subject)
                    .body(emailBody)
                    .htmlBody(true)
                    .build();
            this.digiwfEmailApi.sendMailWithDefaultLogo(mail);
        } catch (final MessagingException ex) {
            log.warn("Notification failed: {}", ex.getMessage());
            throw ex;
        }
    }

    private void notifyCandidateUsers(final DelegateTask delegateTask) {
        if (delegateTask.getCandidates().isEmpty()) {
            return;
        }

        final List<String> addresses = new ArrayList<>();
        for (final IdentityLink link : delegateTask.getCandidates()) {
            if (!IdentityLinkType.CANDIDATE.equals(link.getType())) {
                continue;
            }
            if (StringUtils.isEmpty(link.getUserId())) {
                continue;
            }
            try {
                val address = this.getMailAddress(link.getUserId());
                addresses.add(address);
            } catch (final Exception ex) {
                log.warn(ex.toString());
            }
        }
        if (!addresses.isEmpty()) {
            this.sendGroupMail(addresses, delegateTask);
        }
    }

    private void notifyCandidateGroups(final DelegateTask delegateTask) {
        if (delegateTask.getCandidates().isEmpty()) {
            return;
        }

        final List<String> addresses = new ArrayList<>();
        for (final IdentityLink link : delegateTask.getCandidates()) {
            if (!IdentityLinkType.CANDIDATE.equals(link.getType())) {
                continue;
            }
            if (StringUtils.isEmpty(link.getGroupId())) {
                continue;
            }
            try {
                final Optional<User> ou = this.userService.getOuByShortName(link.getGroupId());
                if (!ou.isPresent()) {
                    log.warn("lhmObject {} was not found", link.getGroupId());
                    continue;
                }
                if (StringUtils.isEmpty(ou.get().getEmail())) {
                    log.warn("lhmObject {} has no mail address", link.getGroupId());
                    continue;
                }

                addresses.add(ou.get().getEmail());
            } catch (final Exception ex) {
                log.warn(ex.toString());
            }
        }

        if (!addresses.isEmpty()) {
            this.sendGroupMail(addresses, delegateTask);
        }
    }

    private String getMailAddress(final String receiver) {
        final User user = this.userService.getUser(receiver);
        if (!StringUtils.isEmpty(user.getEmail())) {
            return user.getEmail();
        }
        throw new RuntimeException("lhmObject {} has no mail address" + receiver);
    }

    private void sendGroupMail(final List<String> addresses, final DelegateTask delegateTask) {
        try {
            String processName = this.getProcessName(delegateTask.getProcessDefinitionId());

            final String subject = TaskVariables.MAIL_SUBJECT.from(delegateTask).getOrDefault("Es liegt eine neue Gruppenaufgabe für Sie bereit");
            final String body = TaskVariables.MAIL_BODY.from(delegateTask).getOrDefault(this.getBodyGroupMail(processName));
            final String bottomText = TaskVariables.MAIL_BOTTOM_TEXT.from(delegateTask).getOrDefault("");
            final String addressList = String.join(",", addresses);

            final Map<String, String> emailContent = Map.of(
                    "%%body_top%%", body,
                    "%%body_bottom%%", bottomText,
                    "%%button_link%%", this.properties.getFrontendUrl() + "/#/opengrouptask/" + delegateTask.getId(),
                    "%%button_text%%", "Gruppenaufgabe öffnen",
                    "%%footer%%", "DigiWF 2.0<br>IT-Referat der Stadt München"
            );
            final String templatePath = "bausteine/mail/templatewithlink/mail-template.tpl";
            final String emailBody = this.digiwfEmailApi.getEmailBodyFromTemplate(templatePath, emailContent);

            final Mail mail = Mail.builder()
                    .receivers(addressList)
                    .subject(subject)
                    .body(emailBody)
                    .htmlBody(true)
                    .build();
            this.digiwfEmailApi.sendMailWithDefaultLogo(mail);
        } catch (final MessagingException ex) {
            log.warn("Notification failed: {}", ex.getMessage());
        }

    }

    private String getProcessName(String processDefinitionId){
        String processName = "";
        try {
            ProcessDefinition procDef = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
            if(procDef.getName() != null && !procDef.getName().isBlank()) {
                processName = procDef.getName();
            }
            else {
                if(procDef.getKey() != null && !procDef.getKey().isBlank()){
                    processName = procDef.getKey();
                }
            }
        }
        catch (Exception ex){
            log.warn("Reading ProcessDefinition failed: {}", ex.getMessage());
        }
        return processName;
    }

    private String getBodyAssignee(String processName) {
        if (!processName.isBlank()){
            return "Sie haben eine Aufgabe in DigiWF (" + processName + ").";
        }
        return "Sie haben eine Aufgabe in DigiWF.";
    }

    private String getBodyGroupMail(String processName) {
        if (!processName.isBlank()){
            return "Sie haben eine Gruppenaufgabe in DigiWF (" + processName + ").";
        }
        return "Sie haben eine Gruppenaufgabe in DigiWF.";
    }

}
