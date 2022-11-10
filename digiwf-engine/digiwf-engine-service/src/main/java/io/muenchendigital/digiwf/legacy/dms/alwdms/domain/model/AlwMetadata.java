/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.dms.alwdms.domain.model;

import lombok.*;

import java.io.Serializable;

/**
 * Represents the metadata of a document(Schriftstück).
 *
 * @author externer.dl.horn
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class AlwMetadata implements Serializable {

    /**
     * Name of the document.
     */
    private final String name;

    /**
     * Type of the document.
     */
    private final String type;

    /**
     * Extension of the document.
     */
    private final String extension;

    /**
     * Size of the document.
     */
    private final String contentSize;

    /**
     * Url of the document.
     */
    private String url;

    /**
     * Set the url of the document
     *
     * @param url
     */
    public void setUrl(final String url) {
        if (url.startsWith("http")) {
            this.url = url;
        } else {
            this.url = "https://" + url;
        }
    }
}
