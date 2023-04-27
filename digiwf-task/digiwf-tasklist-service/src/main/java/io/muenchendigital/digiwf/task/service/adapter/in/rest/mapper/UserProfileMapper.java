package io.muenchendigital.digiwf.task.service.adapter.in.rest.mapper;

import io.muenchendigital.digiwf.task.service.application.port.in.rest.model.UserProfileTO;
import io.muenchendigital.digiwf.task.service.domain.UserProfile;
import lombok.val;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = DateMapper.class)
public interface UserProfileMapper {


  default UserProfileTO to(UserProfile profile) {
    val to = new UserProfileTO();
    to.setFirstName(profile.getFirstName());
    to.setLastName(profile.getLastName());
    to.setUserId(profile.getUserId());
    to.setPrimaryOrgUnit(profile.getPrimaryOrgUnit());

    to.setLongName(String.format("%s %s", to.getFirstName(), to.getLastName()));
    to.setLongNameWithOrgUnit(String.format("%s %s (%s)", to.getFirstName(), to.getLastName(), to.getPrimaryOrgUnit()));
    return to;
  }
}
