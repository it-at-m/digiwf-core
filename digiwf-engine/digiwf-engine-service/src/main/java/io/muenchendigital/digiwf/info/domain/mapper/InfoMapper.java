/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.info.domain.mapper;

import io.muenchendigital.digiwf.info.domain.model.Info;
import io.muenchendigital.digiwf.info.infrastructure.entity.InfoEntity;
import io.muenchendigital.digiwf.legacy.shared.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * Map between {@link InfoEntity} and {@link Info}
 *
 * @author martin.dietrich
 */
@Mapper
public interface InfoMapper extends BaseEntityMapper<Info, InfoEntity> {

}
