/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.form.domain.model;

import io.muenchendigital.digiwf.legacy.form.api.transport.ButtonTO;
import lombok.*;

/**
 * Buttons obejct.
 * Includes all available buttons for the form.
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Buttons {

    /**
     * Complete button.
     */
    private final ButtonTO complete;

    /**
     * Cancel button.
     */
    private final ButtonTO cancel;

    /**
     * Status PDF button.
     */
    private final ButtonTO statusPdf;

}
