/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.form.api.transport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Button object.
 *
 * @author martin.dietrich
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ButtonTO {

    /**
     * Indicates whether a button should be displayed or not
     */
    private boolean showButton;

    /**
     * Button display text
     */
    private String buttonText;

}
