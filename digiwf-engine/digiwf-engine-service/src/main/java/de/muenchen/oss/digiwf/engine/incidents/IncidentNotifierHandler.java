/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.engine.incidents;

import de.muenchen.oss.digiwf.email.api.DigiwfEmailApi;
import de.muenchen.oss.digiwf.email.model.Mail;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.logging.log4j.util.Strings;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.impl.incident.DefaultIncidentHandler;
import org.camunda.bpm.engine.impl.incident.IncidentContext;
import org.camunda.bpm.engine.impl.persistence.entity.IncidentEntity;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.Incident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class IncidentNotifierHandler extends DefaultIncidentHandler {

    @Autowired
    private DigiwfEmailApi digiwfEmailApi;

    @Autowired
    @Lazy
    private RepositoryService repositoryService;

    @Value("${digiwf.incident.cockpitUrl:#{null}}")
    private String cockpitUrl;

    @Value("${digiwf.incident.fromAddress:#{null}}")
    private String fromAddress;

    @Value("${digiwf.incident.toAddress:#{null}}")
    private String toAddress;

    @Value("${digiwf.incident.environment:#{null}}")
    private String environment;

    public IncidentNotifierHandler() {
        super("failedJob");
    }

    @Override
    public Incident handleIncident(final IncidentContext context, final String message) {
        log.warn("Incident occurred");
        final IncidentEntity incidentEntity = (IncidentEntity) super.handleIncident(context, message);

        if (Strings.isEmpty(this.toAddress)) {
            log.debug("Notification on incidents if not configured");
            return incidentEntity;
        }

        try {
            String processName = this.getProcessName(incidentEntity.getProcessDefinitionId());
            val link = this.cockpitUrl +
                    "camunda/app/cockpit/default/#/process-instance/" +
                    incidentEntity.getProcessInstanceId() +
                    "/runtime";
            final String emailText = processName.isBlank() ?
                    "In der Anwendung ist ein Incident aufgetreten." :
                    "In der Anwendung ist ein Incident aufgetreten (Prozessname: " + processName + ").";

            final Map<String, String> emailContent = Map.of(
                    "%%body_top%%", emailText,
                    "%%body_bottom%%", "Mit freundlichen Grüßen<br>Ihr DigiWF-Team",
                    "%%button_link%%", link,
                    "%%button_text%%", "Fehler im Cockpit anzeigen",
                    "%%footer%%", "DigiWF 2.0<br>IT-Referat der Stadt München"
            );
            final String templatePath = "bausteine/mail/templatewithlink/mail-template.tpl";
            final String emailBody = this.digiwfEmailApi.getEmailBodyFromTemplate(templatePath, emailContent);

            final Mail mail = Mail.builder()
                    .receivers(this.toAddress)
                    .subject(this.environment + ": Incident aufgetreten")
                    .body(emailBody)
                    .htmlBody(true)
                    .replyTo(this.fromAddress)
                    .build();
            this.digiwfEmailApi.sendMailWithDefaultLogo(mail);
        } catch (final MessagingException error) {
            log.error("Die Mail für den Incident konnte nicht gesendet werden.", error);
        }

        return incidentEntity;
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
}
