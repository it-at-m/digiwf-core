/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.engine.incidents;

import de.muenchen.oss.digiwf.legacy.mailing.domain.model.MailTemplate;
import de.muenchen.oss.digiwf.legacy.mailing.domain.service.MailingService;
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

import java.io.IOException;


/**
 * Handler getting active in case of incidents and can react e.g. by sending an information mail.
 *
 * @author externer.dl.horn
 */
@Slf4j
@Component
public class IncidentNotifierHandler extends DefaultIncidentHandler {

    @Autowired
    private MailingService mailingService;

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
            this.sendInfoMail(incidentEntity);
        } catch (final Exception error) {
            log.error("Die Mail für den Incident konnte nicht gesendet werden.", error);
        }

        return incidentEntity;
    }

    public void sendInfoMail(final IncidentEntity incidentEntity) throws IOException {

        String processName = this.getProcessName(incidentEntity.getProcessDefinitionId());

        val link = this.cockpitUrl +
                "camunda/app/cockpit/default/#/process-instance/" +
                incidentEntity.getProcessInstanceId() +
                "/runtime";

        String body = "In der Anwendung ist ein Incident aufgetreten.";
        if (!processName.isBlank()){
            body = "In der Anwendung ist ein Incident aufgetreten (Prozessname: " + processName + ").";
        }

        final MailTemplate mail = MailTemplate.builder()
                .body(body)
                .link(link)
                .buttonText("Fehler im Cockpit anzeigen")
                .subject(this.environment + ": Incident aufgetreten")
                .receivers(this.toAddress)
                .replyTo(this.fromAddress)
                .build();
        log.debug("Sending mail");
        this.mailingService.sendMailTemplateWithLink(mail);
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
