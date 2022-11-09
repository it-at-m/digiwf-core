/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.dms.muc.domain.model;

import lombok.*;

/**
 * Objekt, um eine neue Sachakte anzulegen.
 * Eine Sachakte ist eine Akte, in der Vorgänge enthalten sein können.
 *
 * @author externer.dl.horn
 */
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class NeueSachakte {

    /**
     * Id des Aktenplans
     */
    private String aktenplanId;

    /**
     * Name der Sachakte
     */
    private String kurzname;

    /**
     * Betreff der Sachakte
     */
    private String betreff;

    /**
     * TODO
     */
    private String zugriffsDefinitionVorgaenge;

    public NeueSachakte(final String aktenplanId, final String kurzname) {
        this.aktenplanId = aktenplanId;
        this.kurzname = kurzname;
        this.betreff = "";
        this.zugriffsDefinitionVorgaenge = "";
    }

}
