/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.form.domain.model;

import lombok.*;

/**
 * Item of a select field.
 *
 * @author externer.dl.horn
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Item {

    /**
     * Display name of the item.
     */
    private final String name;

    /**
     * Value of the item.
     * Used for storing in the db.
     */
    private final String value;

}
