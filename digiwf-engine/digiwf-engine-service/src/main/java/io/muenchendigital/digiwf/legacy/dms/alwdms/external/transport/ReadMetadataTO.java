/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.legacy.dms.alwdms.external.transport;

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
public class ReadMetadataTO {

    /**
     * Name of the document.
     */
    private String name;

    /**
     * Type of the document.
     */
    private String type;

    /**
     * Extension of the document.
     */
    private String extension;

    /**
     * Size of the document.
     */
    private String contentSize;

}
