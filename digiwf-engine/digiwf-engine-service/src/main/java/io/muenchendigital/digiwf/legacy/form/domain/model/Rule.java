/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.form.domain.model;

import lombok.*;

/**
 * Rule of a form field.
 * Used for validation in the frontend.
 *
 * @author externer.dl.horn
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Rule {

    /**
     * Type of the rule.
     */
    private final String type;

    /**
     * Value fo the rule.
     */
    private final String value;

    /**
     * Target of the rule.
     * Used for required on validation.
     */
    private final String target;

}
