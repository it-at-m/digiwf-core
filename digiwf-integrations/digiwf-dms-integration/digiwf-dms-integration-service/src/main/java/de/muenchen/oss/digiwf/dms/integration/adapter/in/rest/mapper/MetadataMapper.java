package de.muenchen.oss.digiwf.dms.integration.adapter.in.rest.mapper;

import de.muenchen.oss.digiwf.dms.integration.domain.Metadata;
import de.muenchen.oss.digiwf.dms.integration.application.port.in.rest.model.MetadataTO;

import lombok.val;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MetadataMapper {

  default MetadataTO to(Metadata metadata) {
    val to = new MetadataTO();
    to.setName(metadata.getName());
    to.setType(metadata.getType());
    to.setUrl(metadata.getUrl());
    return to;
  }
}
