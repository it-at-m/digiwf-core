/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.mailing.domain.configuration;

import io.muenchendigital.digiwf.legacy.mailing.domain.model.Mail;
import io.muenchendigital.digiwf.legacy.mailing.domain.model.MailTemplate;
import io.muenchendigital.digiwf.legacy.mailing.properties.MailProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mail Configuration Handler.
 * Overrides mail values if configured.
 *
 * @author externer.dl.horn
 */
@Component
@RequiredArgsConstructor
public class MailConfigurationHandler {

    private final MailProperties mailProperties;

    public void handleMail(final Mail mail) {
        if (this.mailProperties.isOverrideReceivers()) {
            mail.updateReceivers(this.mailProperties.getDefaultReceiverAddress());
        }
    }

    public void handleMail(final MailTemplate mail) {
        if (this.mailProperties.isOverrideReceivers()) {
            mail.updateReceivers(this.mailProperties.getDefaultReceiverAddress());
        }
    }

}
