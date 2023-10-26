/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.form.api.transport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Rule of a form field.
 * Used for validation in the frontend.
 *
 * @author externer.dl.horn
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleTO {

    /**
     * Type of the rule.
     */
    private String type;

    /**
     * Value fo the rule.
     */
    private String value;

    /**
     * Target of the rule.
     * Used for required on validation.
     */
    private String target;

}
