/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.file.domain.mapper;

import io.muenchendigital.digiwf.legacy.file.domain.model.File;
import io.muenchendigital.digiwf.legacy.file.infrastructure.entity.FileEntity;
import io.muenchendigital.digiwf.legacy.shared.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * Map between {@link File} and {@link FileEntity}
 *
 * @author externer.dl.horn
 */
@Mapper
public interface FileMapper extends BaseEntityMapper<File, FileEntity> {
}
