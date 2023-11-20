/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.engine.basis.process;

import de.muenchen.oss.digiwf.shared.properties.DigitalWFProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Collection of digitalwf functions for modelling processes.
 *
 * @author externer.dl.horn
 * @deprecated Use {@link AppModellingFunctions} instead
 */
@Component("digitalwf")
@RequiredArgsConstructor
@Deprecated
public class DigitalWFFunctions {

    private final DigitalWFProperties properties;

    /**
     * This methods returns the frontend URL to "Meine Aufgaben"
     *
     * @return url
     */
    public String urlMeineAufgaben() {
        return this.getFrontendUrl() + "#/mytask";
    }

    /**
     * This methods returns the frontend URL to "Gruppenaufgaben offen"
     *
     * @return url
     */
    public String urlGruppenaufgaben() {
        return this.getFrontendUrl() + "#/opengrouptask";
    }

    public String getFrontendUrl() {
        return this.properties.getFrontendUrl().endsWith("/") ? this.properties.getFrontendUrl() : this.properties.getFrontendUrl() + "/";
    }

}
