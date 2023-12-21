/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.process.instance.domain.mapper;

import de.muenchen.oss.digiwf.process.instance.domain.model.ServiceInstance;
import de.muenchen.oss.digiwf.process.instance.domain.model.ServiceInstanceDetail;
import de.muenchen.oss.digiwf.process.instance.infrastructure.entity.ServiceInstanceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Map to {@link ServiceInstance}
 *
 * @author externer.dl.horn
 */
@Mapper
public interface ServiceInstanceMapper {

    List<ServiceInstance> map2Model(List<ServiceInstanceEntity> list);

    @Mapping(target = "processConfig", ignore = true)
    @Mapping(target = "historyTasks", ignore = true)
    @Mapping(target = "data", ignore = true)
    @Mapping(target = "jsonSchema", ignore = true)
    ServiceInstanceDetail map2Detail(ServiceInstance obj);

    ServiceInstance map2Model(ServiceInstanceEntity obj);

    ServiceInstanceEntity map2Entity(ServiceInstance serviceInstance);

}
