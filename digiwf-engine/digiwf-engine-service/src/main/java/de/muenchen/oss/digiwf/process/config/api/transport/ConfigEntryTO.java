/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.process.config.api.transport;

import de.muenchen.oss.digiwf.process.config.domain.model.ConfigEntry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Transport object of the {@link ConfigEntry}
 *
 * @author externer.dl.horn
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfigEntryTO {

    /**
     * Key of the config entry.
     */
    @NotBlank
    private String key;

    /**
     * Value of the config.
     */
    @NotBlank
    private String value;

}
