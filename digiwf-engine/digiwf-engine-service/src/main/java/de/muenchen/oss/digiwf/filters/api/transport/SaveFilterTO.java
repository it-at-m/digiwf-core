/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.filters.api.transport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

/**
 * Detail transport object of a filter.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveFilterTO {

    /**
     * String of the filter.
     */
    @NotNull
    private String filterString;

    /**
     * Id of the page.
     */
    @NotNull
    private String pageId;

}
