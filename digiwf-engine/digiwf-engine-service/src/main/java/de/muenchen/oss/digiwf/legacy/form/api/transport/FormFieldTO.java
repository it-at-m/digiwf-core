/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.form.api.transport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * FormField object.
 * Contains configuration for the UI
 *
 * @author externer.dl.horn
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormFieldTO {

    /**
     * Type of the form field.
     */
    @NotBlank
    private String type;

    /**
     * Key of the form field.
     */
    @NotBlank
    private String key;

    /**
     * Default value that is used if no value is present.
     */
    private String defaultValue;

    /**
     * Default value field is used to fill a value from the data.
     */
    private String defaultValueField;

    /**
     * Label of the field.
     */
    private String label;

    /**
     * Prepend icon for the input field.
     */
    private String prependIcon;

    /**
     * Tooltip of the field.
     */
    private String tooltip;

    /**
     * Specifies the exact type of the input field.
     * Relevant for text fields
     */
    private String ext;

    /**
     * Indicates whether it is a multiple selection.
     * Relevant for select fields
     */
    private boolean multiple;

    /**
     * Description of the field.
     */
    private String description;

    /**
     * Ldap groups are relevant for the ldap-input.
     * Restrict the field to the specified groups.
     */
    private String ldapOus;

    /**
     * Height of the image.
     * Relevant for the image field.
     */
    private String imageHeight;

    /**
     * Width of the image.
     * Relevant for the image field.
     */
    private String imageWidth;

    /**
     * Indicates if the field is readonly.
     * Readonly fields are filtered when a form is completed.
     */
    private boolean readonly;

    /**
     * Rows of the textarea.
     */
    private Integer rows;

    /**
     * Width of the field.
     * Between 1 and 12.
     */
    @Builder.Default
    private Integer col = 12;

    /**
     * Items of the select field.
     */
    @Builder.Default
    private List<ItemTO> items = new ArrayList<>();

    /**
     * Rules of the field.
     * Used for validation in the frontend.
     */
    @Builder.Default
    private List<RuleTO> rules = new ArrayList<>();

    @Builder.Default
    private String itemText = "name";

    @Builder.Default
    private String itemValue = "value";

    @Builder.Default
    private Boolean returnObject = false;
}
