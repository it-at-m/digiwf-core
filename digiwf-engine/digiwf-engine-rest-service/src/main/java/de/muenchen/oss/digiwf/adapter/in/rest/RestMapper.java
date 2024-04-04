package de.muenchen.oss.digiwf.adapter.in.rest;

import de.muenchen.oss.digiwf.domain.Group;
import org.camunda.bpm.engine.rest.dto.identity.GroupDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface RestMapper {

    GroupDto toDto(Group group);
}
