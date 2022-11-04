/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.digitalwf.info.domain.mapper;

import de.muenchen.digitalwf.info.domain.model.Info;
import de.muenchen.digitalwf.info.infrastructure.entity.InfoEntity;
import de.muenchen.digitalwf.legacy.shared.mapper.BaseEntityMapper;
import org.mapstruct.Mapper;

/**
 * Map between {@link InfoEntity} and {@link Info}
 *
 * @author martin.dietrich
 */
@Mapper
public interface InfoMapper extends BaseEntityMapper<Info, InfoEntity> {

}
