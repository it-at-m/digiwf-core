/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.engine.incidents;

import de.muenchen.oss.digiwf.email.api.DigiwfEmailApi;
import de.muenchen.oss.digiwf.email.model.Mail;
import de.muenchen.oss.digiwf.process.config.domain.model.ProcessConfig;
import de.muenchen.oss.digiwf.process.config.domain.service.ProcessConfigService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.logging.log4j.util.Strings;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.incident.IncidentContext;
import org.camunda.bpm.engine.impl.persistence.entity.IncidentEntity;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.Incident;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Handler getting active in case of incidents and can react e.g. by sending an information mail.
 *
 * @author externer.dl.horn
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = { @Lazy })
@EnableConfigurationProperties(IncidentNotificationProperties.class)
public class IncidentNotifierHandler extends BaseIncidentHandler {

    private final RepositoryService repositoryService;

    private final RuntimeService runtimeService;

    private final DigiwfEmailApi digiwfEmailApi;

    private final ProcessConfigService processConfigService;

    private final IncidentNotificationProperties incidentNotificationProperties;

    @Override
    public Incident handleIncident(final IncidentContext context, final String message) {
        log.warn("Incident occurred");
        final IncidentEntity incidentEntity = superHandleIncident(context, message);

        val processInstanceId = incidentEntity.getProcessInstanceId();
        val rootProcessInstanceId = runtimeService
                .createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult()
                .getRootProcessInstanceId();

        val rootProcessDefinitionId = runtimeService
                .createProcessInstanceQuery()
                .processInstanceId(rootProcessInstanceId)
                .singleResult()
                .getProcessDefinitionId();
        val processConfig = processConfigService.getProcessConfig(rootProcessDefinitionId.split(":")[0]);

        var notificationAddresses = processConfig.orElse(new ProcessConfig()).getIncidentNotificationAddresses();

        if (Strings.isEmpty(notificationAddresses)) notificationAddresses = incidentNotificationProperties.getToAddress();

        if (Strings.isEmpty(notificationAddresses)) {
            log.debug("Notification on incidents is not configured");
            return incidentEntity;
        }

        try {
            String processName = this.getProcessName(incidentEntity.getProcessDefinitionId());
            final Map<String, String> emailContent = getEMailContent(incidentEntity, processName);
            final String templatePath = "bausteine/mail/templatewithlink/mail-template.tpl";
            final String emailBody = this.digiwfEmailApi.getEmailBodyFromTemplate(templatePath, emailContent);

            final Mail mail = Mail.builder()
                    .receivers(notificationAddresses)
                    .subject(incidentNotificationProperties.getEnvironment() + ": Incident aufgetreten")
                    .body(emailBody)
                    .htmlBody(true)
                    .replyTo(incidentNotificationProperties.getFromAddress())
                    .build();
            this.digiwfEmailApi.sendMailWithDefaultLogo(mail);
        } catch (final MessagingException error) {
            log.error("Die Mail für den Incident konnte nicht gesendet werden.", error);
        }

        return incidentEntity;
    }

    /**
     * Retrieves the email content for the incident notification email. This includes the process name, a link to the incident in the Camunda Cockpit, and a
     * predefined email template.
     *
     * @param incidentEntity The IncidentEntity representing the incident.
     * @param processName    The name of the process associated with the incident.
     * @return A Map containing the email content key-value pairs.
     */
    @NotNull
    private Map<String, String> getEMailContent(final IncidentEntity incidentEntity, final String processName) {
        val link = incidentNotificationProperties.getCockpitUrl() +
                "camunda/app/cockpit/default/#/process-instance/" +
                incidentEntity.getProcessInstanceId() +
                "/runtime";
        final String emailText = processName.isBlank() ?
                "In der Anwendung ist ein Incident aufgetreten." :
                "In der Anwendung ist ein Incident aufgetreten (Prozessname: " + processName + ").";

        return Map.of(
                "%%body_top%%", emailText,
                "%%body_bottom%%", "Mit freundlichen Grüßen<br>Ihr DigiWF-Team",
                "%%button_link%%", link,
                "%%button_text%%", "Fehler im Cockpit anzeigen",
                "%%footer%%", "DigiWF 2.0<br>IT-Referat der Stadt München"
        );
    }

    private String getProcessName(String processDefinitionId) {
        String processName = "";
        try {
            ProcessDefinition procDef = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
            if (procDef.getName() != null && !procDef.getName().isBlank()) {
                processName = procDef.getName();
            } else {
                if (procDef.getKey() != null && !procDef.getKey().isBlank()) {
                    processName = procDef.getKey();
                }
            }
        } catch (Exception ex) {
            log.warn("Reading ProcessDefinition failed: {}", ex.getMessage());
        }
        return processName;
    }

    IncidentEntity superHandleIncident(final IncidentContext context, final String message) {
        return (IncidentEntity) super.handleIncident(context, message);
    }
}
