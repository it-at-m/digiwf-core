/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.digitalwf.legacy.form.api.mapper;

import de.muenchen.digitalwf.legacy.form.api.transport.FormTO;
import de.muenchen.digitalwf.legacy.form.domain.model.Form;
import de.muenchen.digitalwf.legacy.shared.mapper.BaseTOMapper;
import org.mapstruct.Mapper;

/**
 * Map between {@link FormTO} and {@link Form}
 *
 * @author externer.dl.horn
 */
@Mapper
public interface FormTOMapper extends BaseTOMapper<FormTO, Form> {

}
