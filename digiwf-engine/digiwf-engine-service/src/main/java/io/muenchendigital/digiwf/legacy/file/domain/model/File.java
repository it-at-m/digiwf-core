/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.file.domain.model;

import lombok.*;

/**
 * File object.
 *
 * @author externer.dl.horn
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class File {

    /**
     * Id of the file.
     */
    public String id;

    /**
     * Type of the file.
     */
    public String fileType;

    /**
     * Name of the file
     */
    public String name;

    /**
     * Data of the file
     */
    public String data;

    /**
     * Business key of the corresponding process
     */
    public String businessKey;

    public File(final NewFile file) {
        this.data = file.getData();
        this.fileType = file.getFileType();
        this.name = file.getName();
        this.businessKey = file.getBusinessKey();
    }

}
