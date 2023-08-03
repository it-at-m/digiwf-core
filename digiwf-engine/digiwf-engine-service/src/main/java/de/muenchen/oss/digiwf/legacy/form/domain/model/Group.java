/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.form.domain.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Group of a from.
 * Includes form fields.
 *
 * @author externer.dl.horn
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Group {

    /**
     * Label of the group.
     */
    private final String label;

    /**
     * Schema of the group.
     * Includes form fields.
     */
    @Builder.Default
    private final List<FormField> schema = new ArrayList<>();

    public List<String> getFormFieldKeys() {
        return this.schema.stream()
                .map(FormField::getKey)
                .collect(Collectors.toList());
    }

}
