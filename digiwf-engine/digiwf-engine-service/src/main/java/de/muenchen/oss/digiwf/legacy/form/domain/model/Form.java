/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.form.domain.model;

import de.muenchen.oss.digiwf.legacy.form.domain.validator.ValidationHandler;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Form object.
 * Used for backend validation of the completed forms.
 *
 * @author externer.dl.horn
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Form {

    /**
     * Key of the form.
     */
    @NotBlank
    private final String key;

    /**
     * description of the form.
     */
    private final String description;

    /**
     * authorized groups.
     */
    private final String authorizedGroups;

    /**
     * Buttons of the form.
     */
    private final Buttons buttons;

    /**
     * Sections of the form including all form fields.
     */
    @Size(min = 1, max = 100)
    @Builder.Default
    private final List<Group> groups = new ArrayList<>();

    public List<String> getFormFieldKeys() {
        return this.groups.stream()
                .map(Group::getFormFieldKeys)
                .flatMap(Collection::stream)
                .filter(key -> !StringUtils.isBlank(key))
                .collect(Collectors.toList());
    }

    private List<FormField> getFormFields() {
        return this.groups.stream()
                .map(Group::getSchema)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public boolean validateVariables(final Map<String, Object> variables, final List<ValidationHandler> validationHandlers) {
        final List<String> formFields = this.getFormFieldKeys();
        if (!formFields.containsAll(variables.keySet())) {
            return false;
        }
        return this.getFormFields().stream().allMatch(obj -> obj.validate(variables.get(obj.getKey()), validationHandlers));
    }

    public Optional<FormField> getFormField(final String key) {
        return this.groups.stream()
                .map(Group::getSchema)
                .flatMap(Collection::stream)
                .filter(field -> key.equals(field.getKey()))
                .findFirst();
    }

    public Map<String, FormField> getFormFieldMap() {
        return this.groups.stream()
                .map(Group::getSchema)
                .flatMap(Collection::stream)
                .filter(field -> !StringUtils.isBlank(field.getKey()))
                .collect(Collectors.toMap(FormField::getKey, f -> f));
    }
}
