/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.legacy.dms.alwdms.external.transport;

import lombok.*;

/**
 * Response for the system that calls the read schriftstueck route.
 *
 * @author externer.dl.horn
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ReadSchriftstueckTO {

    /**
     * Name of the document.
     */
    private String filename;

    /**
     * Extension of the document.
     */
    private String fileextension;

    /**
     * Content of the document.
     */
    private byte[] filecontent;

}
