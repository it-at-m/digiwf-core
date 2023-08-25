package de.muenchen.oss.digiwf.ocecosmos.integration.model.mapper;

import de.muenchen.oss.digiwf.ocecosmos.integration.gen.model.Jobs;
import de.muenchen.oss.digiwf.ocecosmos.integration.model.response.JobResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JobResponseMapper {

    @Mapping(target = "jobId", source = "id")
    JobResponse map(Jobs jobs);

}
