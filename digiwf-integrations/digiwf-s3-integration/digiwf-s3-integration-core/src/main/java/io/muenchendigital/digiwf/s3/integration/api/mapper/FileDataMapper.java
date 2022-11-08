package io.muenchendigital.digiwf.s3.integration.api.mapper;

import io.muenchendigital.digiwf.s3.integration.api.dto.FileDataDto;
import io.muenchendigital.digiwf.s3.integration.configuration.MapstructConfiguration;
import io.muenchendigital.digiwf.s3.integration.domain.model.FileData;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class)
public interface FileDataMapper {

    FileData dto2Model(final FileDataDto fileDataDto);

}
