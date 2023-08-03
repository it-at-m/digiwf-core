/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.dms.muc.domain.model;

import de.muenchen.oss.digiwf.legacy.dms.shared.BaseSchriftstueck;
import lombok.*;

import java.io.Serializable;

/**
 * Ein Schriftstück ist ein Dokument, das im DMS gespeichert ist.
 *
 * @author externer.dl.horn
 */
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Schriftstueck implements BaseSchriftstueck, Serializable {

    /**
     * Extension des Schrifstücks.
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

    /**
     * COO des Schriftstücks.
     */
    private String coo;

    public Schriftstueck(final NeuesSchriftstueck schriftstueck, final String coo) {
        this.extension = schriftstueck.getExtension();
        this.name = schriftstueck.getName();
        this.content = schriftstueck.getContent();
        this.coo = coo;
    }
}
