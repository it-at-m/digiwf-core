package io.muenchendigital.digiwf.s3.integration.api.mapper;

import io.muenchendigital.digiwf.s3.integration.api.dto.FilesInFolderDto;
import io.muenchendigital.digiwf.s3.integration.configuration.MapstructConfiguration;
import io.muenchendigital.digiwf.s3.integration.domain.model.FilesInFolder;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class)
public interface FilesInFolderMapper {

    FilesInFolderDto model2Dto(final FilesInFolder filesInFolder);

}
