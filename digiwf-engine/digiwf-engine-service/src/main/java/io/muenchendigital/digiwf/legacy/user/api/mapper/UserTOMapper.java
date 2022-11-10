/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.user.api.mapper;

import io.muenchendigital.digiwf.legacy.shared.mapper.BaseTOMapper;
import io.muenchendigital.digiwf.legacy.user.api.transport.UserTO;
import io.muenchendigital.digiwf.legacy.user.domain.model.User;
import org.mapstruct.Mapper;

/**
 * Map {@link User} domain object into {@link UserTO} transport object.
 *
 * @author externer.dl.horn
 */
@Mapper
public interface UserTOMapper extends BaseTOMapper<UserTO, User> {

}
