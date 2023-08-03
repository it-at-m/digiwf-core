/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.dms.muc.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Vorgang ist ein DMS-Vorgang an dem Dokumente hängen können. Ein Vorgang hängt unter einer Sachakte.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class NeuerVorgang {
    private String sachakteId;
    private String kurzname;
    private String betreff;

    // default elektronisch
    private VorgangArt art = VorgangArt.ELEKTRONISCH;

    public NeuerVorgang(final String sachakteId, final String kurzname, final String betreff) {
        this.sachakteId = sachakteId;
        this.kurzname = kurzname;
        this.betreff = betreff;
    }
}
