package io.muenchendigital.digiwf.s3.integration.adapter.in.rest.mapper;

import io.muenchendigital.digiwf.s3.integration.adapter.in.rest.dto.FileDataDto;
import io.muenchendigital.digiwf.s3.integration.infrastructure.mapper.MapstructConfiguration;
import io.muenchendigital.digiwf.s3.integration.domain.model.FileData;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class)
public interface FileDataMapper {

    FileData dto2Model(final FileDataDto fileDataDto);

}
