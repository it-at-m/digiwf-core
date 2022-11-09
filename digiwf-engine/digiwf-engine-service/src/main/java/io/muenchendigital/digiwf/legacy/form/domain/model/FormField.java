/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.form.domain.model;

import io.muenchendigital.digiwf.legacy.form.domain.validator.ValidationHandler;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Form field obejct.
 *
 * @author externer.dl.horn
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class FormField {

    /**
     * Type of the form field.
     */
    private final String type;

    /**
     * Key of the form field.
     */
    private final String key;

    /**
     * Default value that is used if no value is present.
     */
    private final String defaultValue;

    /**
     * Default value field is used to fill a value from the data.
     */
    private final String defaultValueField;

    /**
     * Label of the field.
     */
    private final String label;

    /**
     * Prepend icon for the input field.
     */
    private final String prependIcon;

    /**
     * Tooltip of the field.
     */
    private final String tooltip;

    /**
     * Specifies the exact type of the input field.
     * Relevant for text fields
     */
    private final String ext;

    /**
     * Indicates whether it is a multiple selection.
     * Relevant for select fields
     */
    private final boolean multiple;

    /**
     * Description of the field.
     */
    private final String description;

    /**
     * Ldap groups are relevant for the ldap-input.
     * Restrict the field to the specified groups.
     */
    private final String ldapOus;

    /**
     * Height of the image.
     * Relevant for the image field.
     */
    private final String imageHeight;

    /**
     * Width of the image.
     * Relevant for the image field.
     */
    private final String imageWidth;

    /**
     * Indicates if the field is readonly.
     * Readonly fields are filtered when a form is completed.
     */
    private final boolean readonly;

    /**
     * Rows of the textarea.
     */
    private final Integer rows;

    /**
     * Width of the field.
     * Between 1 and 12.
     */
    @Builder.Default
    private final Integer col = 12;

    /**
     * Items of the select field.
     */
    @Builder.Default
    private final List<Item> items = new ArrayList<>();

    /**
     * Rules of the field.
     * Used for validation in the frontend.
     */
    @Builder.Default
    private final List<Rule> rules = new ArrayList<>();

    public boolean validate(final Object value, final List<ValidationHandler> validationHandler) {
        return validationHandler.stream().allMatch(obj -> obj.validate(value, this));
    }

}
