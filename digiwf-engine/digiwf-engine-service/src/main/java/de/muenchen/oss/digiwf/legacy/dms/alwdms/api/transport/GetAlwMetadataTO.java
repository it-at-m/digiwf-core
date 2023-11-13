/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.dms.alwdms.api.transport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Object to get alw dms object metadata.
 *
 * @author externer.dl.horn
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAlwMetadataTO {

    /**
     * Url to the corresponding dms.
     * From this the COO is extracted.
     */
    @NotBlank
    private String url;
}
