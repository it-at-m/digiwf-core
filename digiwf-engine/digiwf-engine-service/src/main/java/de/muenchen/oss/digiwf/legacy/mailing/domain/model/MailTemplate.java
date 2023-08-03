/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.mailing.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Extension of the {@link Mail}
 *
 * @author externer.dl.horn
 */
@Setter
@Getter
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class MailTemplate extends Mail {

    /**
     * Bottom text of the mail.
     */
    private String bottomText;

    /**
     * Sender of the mail.
     */
    private String sender;

    /**
     * Footer of the mail
     */
    private String footer;

    /**
     * Link of the mail.
     * Is linked to the button
     */
    private String link;

    /**
     * Button text.
     */
    private String buttonText;

}
