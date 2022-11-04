package de.muenchen.digitalwf.service.definition.api.mapper;

import de.muenchen.digitalwf.legacy.form.api.mapper.FormFieldTOMapper;
import de.muenchen.digitalwf.service.definition.api.transport.ServiceDefinitionDetailTO;
import de.muenchen.digitalwf.service.definition.api.transport.ServiceDefinitionTO;
import de.muenchen.digitalwf.service.definition.domain.model.ServiceDefinition;
import de.muenchen.digitalwf.service.definition.domain.model.ServiceDefinitionDetail;
import org.mapstruct.Mapper;

import java.util.List;

//TODO remove if from is deprecated
@Mapper(uses = FormFieldTOMapper.class)
public interface ServiceDefinitionApiMapper {

    List<ServiceDefinitionTO> map2TO(List<ServiceDefinition> list);

    ServiceDefinitionDetailTO map2TO(ServiceDefinitionDetail obj);

}
