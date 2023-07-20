package io.muenchendigital.digiwf.s3.integration.adapter.in.rest.mapper;

import io.muenchendigital.digiwf.s3.integration.adapter.in.rest.dto.FilesInFolderDto;
import io.muenchendigital.digiwf.s3.integration.infrastructure.mapper.MapstructConfiguration;
import io.muenchendigital.digiwf.s3.integration.domain.model.FilesInFolder;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class)
public interface FilesInFolderMapper {

    FilesInFolderDto model2Dto(final FilesInFolder filesInFolder);

}
