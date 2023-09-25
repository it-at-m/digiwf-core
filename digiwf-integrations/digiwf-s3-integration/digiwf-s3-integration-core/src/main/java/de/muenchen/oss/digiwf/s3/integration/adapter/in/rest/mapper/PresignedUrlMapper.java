package de.muenchen.oss.digiwf.s3.integration.adapter.in.rest.mapper;

import de.muenchen.oss.digiwf.s3.integration.adapter.in.rest.dto.PresignedUrlDto;
import de.muenchen.oss.digiwf.s3.integration.domain.model.PresignedUrl;
import de.muenchen.oss.digiwf.s3.integration.infrastructure.mapper.MapstructConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class)
public interface PresignedUrlMapper {

    PresignedUrlDto model2Dto(final PresignedUrl fileResponse);

    List<PresignedUrlDto> models2Dtos(final List<PresignedUrl> presignedUrls);

}
