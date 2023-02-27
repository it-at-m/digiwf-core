/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.process.config.api.transport;

import io.muenchendigital.digiwf.process.config.domain.model.ProcessConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * Transport object of the {@link ProcessConfig}
 *
 * @author externer.dl.horn
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessConfigTO {

    /**
     * key of the process config.
     */
    @NotBlank
    private String key;

    /**
     * default status dokument.
     */
    private String statusDokument;

    /**
     * status config of the process definition.
     */
    @Builder.Default
    private List<StatusConfigTO> statusConfig = new ArrayList<>();

    /**
     * dynamic config entries.
     */
    @Builder.Default
    private List<ConfigEntryTO> configs = new ArrayList<>();
}
