/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.mailing.domain.model;

import lombok.*;

/**
 * Mail Attachement.
 * Can be anything e.g. a pdf document.
 *
 * @author externer.dl.horn
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Attachment {

    /**
     * Name of the attachment.
     */
    private final String name;

    /**
     * Content of the attachement.
     */
    private final byte[] content;
}
