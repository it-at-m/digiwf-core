/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.info.api.transport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Info transfer object.
 *
 * @author martin.dietrich
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InfoTO {

    /**
     * Maintenance info1.
     */
    private String maintenanceInfo1;

    /**
     * Maintenance info2.
     */
    private String maintenanceInfo2;

    /**
     * Environment name.
     */
    private String environment;

}
