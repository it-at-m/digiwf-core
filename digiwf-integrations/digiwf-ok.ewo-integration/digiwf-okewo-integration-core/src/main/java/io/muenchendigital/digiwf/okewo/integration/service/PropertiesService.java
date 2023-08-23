package io.muenchendigital.digiwf.okewo.integration.service;

import io.muenchendigital.digiwf.okewo.integration.gen.model.BenutzerType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PropertiesService {

    @Getter
    private final String benutzerId;

    public BenutzerType getBenutzerTypeWithBenutzerId() {
        final var benutzerType = new BenutzerType();
        benutzerType.setBenutzerId(this.getBenutzerId());
        return benutzerType;
    }

}
