/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.dms.integration.adapter.out.fabasoft;

/**
 * Status code of the request.
 *
 * @author martin.dietrich
 */
public enum DMSStatusCode {
    UEBERTRAGUNG_ERFORLGREICH(0),
    OBJEKT_GESPERRT(1),
    FEHLENDE_BERECHTIGUNG(2),
    UNGUELTIGE_ADRESSE(3),
    MEHR_ALS_1000_UNTERGEORDNETE_OBJEKTE(4),
    AUFRUF_OBJEKT_FALSCHER_FEHLERKLASSE(5),
    HINWEIS_LESEN_VON_STORNIERTEM_OBJEKT(6),
    FALSCHE_ZUGRIFFSDEFINITION(7),
    FALSCHER_AKTENPLANEINTRAG(8),
    NICHT_PLAUSIBEL(9),
    OBJEKT_ZU_GROSS_FUER_UEBERTRAGUNG_MIT_SOAP(10),
    UNBEKANNTER_FEHLER(-1);

    private final int statuscode;

    DMSStatusCode(final int statuscode) {
        this.statuscode = statuscode;
    }

    public static DMSStatusCode byCode(final int code) {
        for (final DMSStatusCode status : DMSStatusCode.values()) {
            if (status.statuscode == code) {
                return status;
            }
        }
        return UNBEKANNTER_FEHLER;
    }

    public int getStatuscode() {
        return this.statuscode;
    }
}
