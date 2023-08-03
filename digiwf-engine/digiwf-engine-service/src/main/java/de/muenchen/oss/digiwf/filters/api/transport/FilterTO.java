/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.filters.api.transport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Detail transport object of a filter.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterTO {

    /**
     * Id of the filter.
     */
    private String id;

    /**
     * String of the filter.
     */
    private String filterString;

    /**
     * Id of the page.
     */
    private String pageId;

}
