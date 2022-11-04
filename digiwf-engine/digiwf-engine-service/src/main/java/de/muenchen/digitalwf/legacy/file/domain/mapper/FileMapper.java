/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.digitalwf.legacy.file.domain.mapper;

import de.muenchen.digitalwf.legacy.file.domain.model.File;
import de.muenchen.digitalwf.legacy.file.infrastructure.entity.FileEntity;
import de.muenchen.digitalwf.legacy.shared.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * Map between {@link File} and {@link FileEntity}
 *
 * @author externer.dl.horn
 */
@Mapper
public interface FileMapper extends BaseEntityMapper<File, FileEntity> {
}
