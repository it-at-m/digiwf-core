package de.muenchen.oss.digiwf.ocecosmos.integration.api.mapper;

import de.muenchen.oss.digiwf.ocecosmos.integration.api.configuration.MapstructConfiguration;
import de.muenchen.oss.digiwf.ocecosmos.integration.api.dto.request.BaseJobRequestDto;
import de.muenchen.oss.digiwf.ocecosmos.integration.api.dto.request.FileJobRequestDto;
import de.muenchen.oss.digiwf.ocecosmos.integration.api.dto.request.TemplateJobRequestDto;
import de.muenchen.oss.digiwf.ocecosmos.integration.model.request.JobRequest;
import lombok.Data;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.SubclassMapping;
import org.springframework.beans.factory.annotation.Value;

@Mapper(config = MapstructConfiguration.class)
@Data
public abstract class OceCosmosMapper {

    @Value("${de.muenchen.oss.digiwf.ocecosmos.defaults.applicationName:}")
    private String applicationNameDefault;
    @Value("${de.muenchen.oss.digiwf.ocecosmos.defaults.templateName:}")
    private String templateNameDefault;
    @Value("${de.muenchen.oss.digiwf.ocecosmos.defaults.jobType:}")
    private String jobTypeDefault;
    @Value("${de.muenchen.oss.digiwf.ocecosmos.defaults.debtor:}")
    private String debtorDefault;
    @Value("${de.muenchen.oss.digiwf.ocecosmos.defaults.printJob:true}")
    private Boolean printJobDefault;
    @Value("${de.muenchen.oss.digiwf.ocecosmos.defaults.mailJob:false}")
    private Boolean mailJobDefault;
    @Value("${de.muenchen.oss.digiwf.ocecosmos.defaults.archiveJob:false}")
    private Boolean archiveJobDefault;
    @Value("${de.muenchen.oss.digiwf.ocecosmos.defaults.hostName:}")
    private String hostNameDefault;
    @Value("${de.muenchen.oss.digiwf.ocecosmos.defaults.userId:}")
    private String userIdDefault;
    @Value("${de.muenchen.oss.digiwf.ocecosmos.oauth-client.clientId}")
    private String clientId;

    @Mapping(target = "jobType", source = "jobType", defaultExpression = "java(getJobTypeDefault())")
    @Mapping(target = "applicationName", source = "applicationName", defaultExpression = "java(getApplicationNameDefault())")
    @Mapping(target = "debtor", source = "debtor", defaultExpression = "java(getDebtorDefault())")
    @Mapping(target = "printJob", source = "printJob", defaultExpression = "java(getPrintJobDefault())", qualifiedByName = "BooleanMapper")
    @Mapping(target = "mailJob", source = "mailJob", defaultExpression = "java(getMailJobDefault())", qualifiedByName = "BooleanMapper")
    @Mapping(target = "archiveJob", source = "archiveJob", defaultExpression = "java(getArchiveJobDefault())", qualifiedByName = "BooleanMapper")
    @Mapping(target = "hostName", source = "hostName", defaultExpression = "java(getHostNameDefault())")
    @Mapping(target = "userId", source = "userId", defaultExpression = "java(getUserIdDefault())")
    @Mapping(target = "dataType", ignore = true)
    @Mapping(target = "clientId", expression = "java(getClientId())")
    @Mapping(target = "file", ignore = true)
    @Mapping(target = "fileName", ignore = true)
    @Mapping(target = "templateName", ignore = true)
    @SubclassMapping(target = JobRequest.class, source = TemplateJobRequestDto.class)
    @SubclassMapping(target = JobRequest.class, source = FileJobRequestDto.class)
    public abstract JobRequest baseDto2Model(final BaseJobRequestDto baseJobRequestDto);

    @InheritConfiguration(name = "baseDto2Model")
    @Mapping(target = "fileName", source = "fileName")
    @Mapping(target = "templateName", source = "templateName")
    @Mapping(target = "dataType", source = "dataType", defaultValue = "JSON")
    public abstract JobRequest dto2Model(final TemplateJobRequestDto templateJobRequestDto);

    @InheritConfiguration(name = "baseDto2Model")
    @Mapping(target = "dataType", source = "dataType", defaultValue = "PDF")
    @Mapping(target = "templateName", expression = "java(getTemplateNameDefault())" )
    public abstract JobRequest dto2Model(final FileJobRequestDto fileJobRequestDto);

    @Named("BooleanMapper")
    public boolean mapBoolean(Boolean bool) {
        return bool;
    }

}
