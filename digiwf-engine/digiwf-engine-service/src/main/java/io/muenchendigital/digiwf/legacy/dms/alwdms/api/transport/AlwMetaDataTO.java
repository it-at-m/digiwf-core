/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.dms.alwdms.api.transport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Transport document metadata object.
 *
 * @author externer.dl.horn
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlwMetaDataTO {

    /**
     * Name of the document.
     */
    @NotBlank
    private String name;

    /**
     * Type of the document.
     */
    private String type;

    /**
     * Url of the document.
     */
    private String url;

    /**
     * Extension of the document
     */
    private String extension;

    /**
     * Size of the document.
     */
    private String contentSize;
}
