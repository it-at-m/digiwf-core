/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.dms.muc.domain.model;

import lombok.*;

/**
 * Vorgang ist ein DMS-Vorgang an dem Dokumente hängen können. Ein Vorgang hängt unter einer Sachakte.
 */
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Vorgang {

    /**
     * Id der Sachakte
     */
    private String sachakteId;

    /**
     * COO des Vorgangs
     */
    private String coo;

    /**
     * Name des Vorgangs
     */
    private String kurzname;

    /**
     * Betreff des Vorgangs
     */
    private String betreff;

    /**
     * Art des Vortgangs.
     * Default ist ELEKTRONISCH
     */
    private VorgangArt art = VorgangArt.ELEKTRONISCH;

    public Vorgang(final NeuerVorgang neuerVorgang, final String coo) {
        this.sachakteId = neuerVorgang.getSachakteId();
        this.kurzname = neuerVorgang.getKurzname();
        this.betreff = neuerVorgang.getBetreff();
        this.coo = coo;
    }
}
