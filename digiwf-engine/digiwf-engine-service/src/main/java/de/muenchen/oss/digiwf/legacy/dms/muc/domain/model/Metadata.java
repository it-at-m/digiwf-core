/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.dms.muc.domain.model;

import lombok.*;

import java.io.Serializable;

/**
 * Metadata representation of a document.
 *
 * @author externer.dl.horn
 */
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Metadata implements Serializable {

    private String name;

    private String type;

    private String url;
}
