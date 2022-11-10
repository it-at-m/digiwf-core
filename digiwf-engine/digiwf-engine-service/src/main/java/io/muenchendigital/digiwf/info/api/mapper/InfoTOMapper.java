/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.info.api.mapper;

import io.muenchendigital.digiwf.info.api.transport.InfoTO;
import io.muenchendigital.digiwf.info.domain.model.Info;
import io.muenchendigital.digiwf.legacy.form.api.transport.FormTO;
import io.muenchendigital.digiwf.legacy.form.domain.model.Form;
import io.muenchendigital.digiwf.legacy.shared.mapper.BaseTOMapper;
import org.mapstruct.Mapper;

/**
 * Map between {@link FormTO} and {@link Form}
 *
 * @author martin.dietrich
 */
@Mapper
public interface InfoTOMapper extends BaseTOMapper<InfoTO, Info> {

}
