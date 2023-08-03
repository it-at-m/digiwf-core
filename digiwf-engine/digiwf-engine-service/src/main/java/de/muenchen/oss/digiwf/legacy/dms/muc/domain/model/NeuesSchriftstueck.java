/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.dms.muc.domain.model;

import lombok.*;

/**
 * Neues Schriftstück.
 * Wird verwendet um ein neues Objekt anzulegen.
 */
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class NeuesSchriftstueck {

    /**
     * Extension des Schriftsücks.
     */
    private String extension;

    /**
     * Name des Schriftstücks.
     */
    private String name;

    /**
     * Inhalt des Schriftstücks.
     */
    private byte[] content;

}
