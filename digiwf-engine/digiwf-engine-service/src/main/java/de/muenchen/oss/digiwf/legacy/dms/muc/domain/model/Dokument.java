/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.dms.muc.domain.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Dokument ist ein DMS-Dokument (Eingehend oder Ausgehened == Incoming, Outgoing).
 */
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Dokument {

    /**
     * COO
     */
    private String coo;

    /**
     * Id des Vorgangs
     */
    private String vorgangId;

    /**
     * Typ es Dokuments - Eingehend / Ausgehend
     */
    private EinAusgehend einAusgehend;

    /**
     * Name des Dokuemnts
     */
    private String kurzname;

    /**
     * Betreff des Dokuments
     */
    private String betreff;

    /**
     * Liste der enthaltenen Schriftstücke
     */
    private List<Schriftstueck> schriftstuecke = new ArrayList<>();

    public Dokument(final NeuesDokument neuesDokument, final String coo, final List<Schriftstueck> schriftstuecke) {
        this.vorgangId = neuesDokument.getVorgangId();
        this.einAusgehend = neuesDokument.getEinAusgehend();
        this.kurzname = neuesDokument.getKurzname();
        this.betreff = neuesDokument.getBetreff();
        this.schriftstuecke.addAll(schriftstuecke);
        this.coo = coo;
    }

    public Dokument(final Dokument dokument, final List<Schriftstueck> schriftstuecke) {
        this.vorgangId = dokument.getVorgangId();
        this.einAusgehend = dokument.getEinAusgehend();
        this.kurzname = dokument.getKurzname();
        this.betreff = dokument.getBetreff();
        if (schriftstuecke != null && !schriftstuecke.isEmpty()) {
            this.schriftstuecke.addAll(schriftstuecke);
        }
        this.coo = dokument.getCoo();
    }
}
