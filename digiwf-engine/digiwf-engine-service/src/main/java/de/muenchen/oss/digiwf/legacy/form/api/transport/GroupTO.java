/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.form.api.transport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Group of a from.
 * Includes form fields.
 *
 * @author externer.dl.horn
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupTO {

    /**
     * Label of the group.
     */
    private String label;

    /**
     * Schema of the group.
     * Includes form fields.
     */
    @Builder.Default
    private List<FormFieldTO> schema = new ArrayList<>();

}
