package de.muenchen.oss.digiwf.okewo.integration.service;

import de.muenchen.oss.digiwf.okewo.integration.gen.model.BenutzerType;
import lombok.Getter;

public class PropertiesServiceTemplate {

    public PropertiesServiceTemplate(String benutzerId) {
        this.benutzerId = benutzerId;
    }

    @Getter
    private final String benutzerId;

    public BenutzerType getBenutzerTypeWithBenutzerId() {
        final var benutzerType = new BenutzerType();
        benutzerType.setBenutzerId(this.benutzerId);
        return benutzerType;
    }

}
