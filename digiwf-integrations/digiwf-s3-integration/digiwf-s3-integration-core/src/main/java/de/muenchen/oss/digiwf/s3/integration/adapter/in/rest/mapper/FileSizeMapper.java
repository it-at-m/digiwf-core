package de.muenchen.oss.digiwf.s3.integration.adapter.in.rest.mapper;

import de.muenchen.oss.digiwf.s3.integration.adapter.in.rest.dto.FileSizeDto;
import de.muenchen.oss.digiwf.s3.integration.domain.model.FileSize;
import de.muenchen.oss.digiwf.s3.integration.infrastructure.mapper.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class)
public interface FileSizeMapper {

    FileSizeDto model2dto(FileSize fileSize);
}
