/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.form.api.transport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Wrapper object for buttons.
 *
 * @author martin.dietrich
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ButtonsTO {

    /**
     * Complete button.
     */
    private ButtonTO complete;

    /**
     * Cancel button.
     */
    private ButtonTO cancel;

    /**
     * Status PDF button.
     */
    private ButtonTO statusPdf;

}
