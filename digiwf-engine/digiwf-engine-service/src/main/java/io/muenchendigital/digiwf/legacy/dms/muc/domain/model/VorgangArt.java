/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.dms.muc.domain.model;

/**
 * Art des Vorgangs.
 */
public enum VorgangArt {
    ELEKTRONISCH("Elektronisch"),
    PAPIER("Papier"),
    HYBRID("Hybrid");

    private String value;

    VorgangArt(final String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
