/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.form.api.mapper;

import de.muenchen.oss.digiwf.legacy.form.api.transport.FormTO;
import de.muenchen.oss.digiwf.legacy.form.domain.model.Form;
import de.muenchen.oss.digiwf.legacy.shared.mapper.BaseTOMapper;
import org.mapstruct.Mapper;

/**
 * Map between {@link FormTO} and {@link Form}
 *
 * @author externer.dl.horn
 */
@Mapper
public interface FormTOMapper extends BaseTOMapper<FormTO, Form> {

}
