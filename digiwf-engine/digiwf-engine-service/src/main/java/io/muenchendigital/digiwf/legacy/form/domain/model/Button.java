/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.form.domain.model;

import lombok.*;

/**
 * Button object.
 *
 * @author martin.dietrich
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Button {

    /**
     * Indicates whether a button should be displayed or not
     */
    private final boolean showButton;

    /**
     * Button display text
     */
    private final String buttonText;

}
