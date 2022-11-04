/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.digitalwf.info.api.mapper;

import de.muenchen.digitalwf.info.api.transport.InfoTO;
import de.muenchen.digitalwf.info.domain.model.Info;
import de.muenchen.digitalwf.legacy.form.api.transport.FormTO;
import de.muenchen.digitalwf.legacy.form.domain.model.Form;
import de.muenchen.digitalwf.legacy.shared.mapper.BaseTOMapper;
import org.mapstruct.Mapper;

/**
 * Map between {@link FormTO} and {@link Form}
 *
 * @author martin.dietrich
 */
@Mapper
public interface InfoTOMapper extends BaseTOMapper<InfoTO, Info> {

}
