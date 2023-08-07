package de.muenchen.oss.digiwf.process.definition.api.mapper;

import de.muenchen.oss.digiwf.legacy.form.api.mapper.FormFieldTOMapper;
import de.muenchen.oss.digiwf.process.definition.api.transport.ServiceDefinitionDetailTO;
import de.muenchen.oss.digiwf.process.definition.api.transport.ServiceDefinitionTO;
import de.muenchen.oss.digiwf.process.definition.domain.model.ServiceDefinition;
import de.muenchen.oss.digiwf.process.definition.domain.model.ServiceDefinitionDetail;
import org.mapstruct.Mapper;

import java.util.List;

//TODO remove if from is deprecated
@Mapper(uses = FormFieldTOMapper.class)
public interface ServiceDefinitionApiMapper {

    List<ServiceDefinitionTO> map2TO(List<ServiceDefinition> list);
    ServiceDefinitionTO map2TO(ServiceDefinition definition);

    ServiceDefinitionDetailTO map2TO(ServiceDefinitionDetail obj);

}
