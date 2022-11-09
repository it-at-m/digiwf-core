/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.file.domain.model;

import lombok.*;

/**
 * New file object.
 * Is used to create a file.
 *
 * @author externer.dl.horn
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class NewFile {

    /**
     * Data of the file.
     */
    public String data;

    /**
     * Name of the file.
     */
    public String name;

    /**
     * Type of the file.
     */
    public String fileType;

    /**
     * Business key of the corresponding process.
     */
    public String businessKey;

}
