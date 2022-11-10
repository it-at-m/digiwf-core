/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.service.instance.domain.mapper;

import io.muenchendigital.digiwf.service.instance.domain.model.ServiceInstance;
import io.muenchendigital.digiwf.service.instance.domain.model.ServiceInstanceDetail;
import io.muenchendigital.digiwf.service.instance.infrastructure.entity.ServiceInstanceEntity;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Map to {@link ServiceInstance}
 *
 * @author externer.dl.horn
 */
@Mapper
public interface ServiceInstanceMapper {

    List<ServiceInstance> map2Model(List<ServiceInstanceEntity> list);

    ServiceInstanceDetail map2Detail(ServiceInstance obj);

    ServiceInstance map2Model(ServiceInstanceEntity obj);

    ServiceInstanceEntity map2Entity(ServiceInstance serviceInstance);

}
