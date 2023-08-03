/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.dms.alwdms.domain.model;

import de.muenchen.oss.digiwf.legacy.dms.shared.BaseSchriftstueck;
import lombok.*;

import java.io.Serializable;

/**
 * Schriftstück coming form the alw dms.
 *
 * @author externer.dl.horn
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class AlwSchriftstueck implements BaseSchriftstueck, Serializable {

    /**
     * Name of the Schriftstück.
     */
    private final String name;

    /**
     * Extension of the Schriftstück.
     */
    private final String extension;

    /**
     * Content of the Schriftstück.
     */
    private final byte[] content;

}
