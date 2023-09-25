package de.muenchen.oss.digiwf.s3.integration.adapter.in.rest.mapper;

import de.muenchen.oss.digiwf.s3.integration.adapter.in.rest.dto.FilesInFolderDto;
import de.muenchen.oss.digiwf.s3.integration.domain.model.FilesInFolder;
import de.muenchen.oss.digiwf.s3.integration.infrastructure.mapper.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class)
public interface FilesInFolderMapper {

    FilesInFolderDto model2Dto(final FilesInFolder filesInFolder);

}
