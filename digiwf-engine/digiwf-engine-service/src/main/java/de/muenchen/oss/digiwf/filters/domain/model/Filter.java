/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.filters.domain.model;

import lombok.*;

/**
 * Detail representation of a filter.
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Filter {

    /**
     * Id of the filter.
     */
    private final String id;

    /**
     * String of the filter.
     */
    private final String filterString;

    /**
     * Id of the user.
     */
    private final String userId;

    /**
     * Id of the page.
     */
    private final String pageId;

}
