/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.mailing.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

/**
 * Object contains all the information needed to send a mail.
 *
 * @author externer.dl.horn
 */
@Setter
@Getter
@ToString
@SuperBuilder
@EqualsAndHashCode
@AllArgsConstructor
public class Mail {

    /**
     * Receivers addresses of the mail.
     */
    private String receivers;

    /**
     * Subject of the mail.
     */
    private String subject;

    /**
     * Body of the mail.
     */
    private String body;

    /**
     * Reply to address
     */
    private String replyTo;

    /**
     * Attachment of the mail.
     */
    private Attachment attachment;

    public boolean hasAttachement() {
        return this.attachment != null && this.attachment.getContent() != null;
    }

    public boolean hasReplyTo() {
        return !StringUtils.isBlank(this.replyTo);
    }

    public void updateReceivers(final String receivers) {
        this.receivers = receivers;
    }

}
