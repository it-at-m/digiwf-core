/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.digitalwf.legacy.user.api.mapper;

import de.muenchen.digitalwf.legacy.shared.mapper.BaseTOMapper;
import de.muenchen.digitalwf.legacy.user.api.transport.UserTO;
import de.muenchen.digitalwf.legacy.user.domain.model.User;
import org.mapstruct.Mapper;

/**
 * Map {@link User} domain object into {@link UserTO} transport object.
 *
 * @author externer.dl.horn
 */
@Mapper
public interface UserTOMapper extends BaseTOMapper<UserTO, User> {

}
