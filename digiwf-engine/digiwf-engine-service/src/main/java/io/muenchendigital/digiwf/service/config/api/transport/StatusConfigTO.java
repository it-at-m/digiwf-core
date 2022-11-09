/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.service.config.api.transport;

import io.muenchendigital.digiwf.service.config.domain.model.StatusConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Transport object of the {@link StatusConfig}
 *
 * @author externer.dl.horn
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatusConfigTO {

    /**
     * Key of the status config.
     */
    @NotBlank
    private String key;

    /**
     * Label of the status config.
     * Used to display the current status.
     */
    @NotBlank
    private String label;

    /**
     * Position of the status config.
     * Used to order them correctly.
     */
    @NotNull
    private Integer position;

}
