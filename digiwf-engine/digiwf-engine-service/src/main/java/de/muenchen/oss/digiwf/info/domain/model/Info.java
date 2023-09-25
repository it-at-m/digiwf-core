/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.info.domain.model;

import lombok.*;

/**
 * Info data object.
 *
 * @author martin.dietrich
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Info {

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
