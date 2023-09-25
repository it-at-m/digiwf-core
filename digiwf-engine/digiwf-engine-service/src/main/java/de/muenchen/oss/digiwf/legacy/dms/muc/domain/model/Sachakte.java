/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.dms.muc.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Sachakte {

    /**
     * COO der Akte
     */
    private String coo;

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

    public Sachakte(final NeueSachakte sachakte, final String coo) {
        this.coo = coo;
        this.aktenplanId = sachakte.getAktenplanId();
        this.kurzname = sachakte.getKurzname();
        this.betreff = sachakte.getBetreff();
        this.zugriffsDefinitionVorgaenge = sachakte.getZugriffsDefinitionVorgaenge();
    }
}
