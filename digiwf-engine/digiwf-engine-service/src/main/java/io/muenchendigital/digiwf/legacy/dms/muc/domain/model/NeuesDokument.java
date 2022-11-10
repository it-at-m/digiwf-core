/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.dms.muc.domain.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Dokument ist ein DMS-Dokument (Eingehend oder Ausgehened == Incoming, Outgoing).
 *
 * @author externer.dl.horn
 */
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class NeuesDokument {

    /**
     * Id des Vorgangs.
     */
    private String vorgangId;

    /**
     * Typ es Dokuments - Eingehend / Ausgehend.
     */
    private EinAusgehend einAusgehend;

    /**
     * Name des Dokuments.
     */
    private String kurzname;

    /**
     * Betreff des Dokuments.
     */
    private String betreff;

    @Builder.Default
    private final List<NeuesSchriftstueck> schriftstuecke = new ArrayList<>();
}
