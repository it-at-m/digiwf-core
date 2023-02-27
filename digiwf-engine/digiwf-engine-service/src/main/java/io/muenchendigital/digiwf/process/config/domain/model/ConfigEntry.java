/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.process.config.domain.model;

import lombok.*;

/**
 * Dynamic configuration entry.
 *
 * @author externer.dl.horn
 */
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ConfigEntry {

    /**
     * Key of the config entry.
     */
    private String key;

    /**
     * Value of the config.
     */
    private String value;

}
