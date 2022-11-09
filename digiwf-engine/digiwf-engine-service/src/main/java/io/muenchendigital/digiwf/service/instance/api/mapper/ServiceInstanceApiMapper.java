/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.service.instance.api.mapper;

import io.muenchendigital.digiwf.service.instance.api.transport.ServiceInstanceDetailTO;
import io.muenchendigital.digiwf.service.instance.api.transport.ServiceInstanceTO;
import io.muenchendigital.digiwf.service.instance.domain.model.ServiceInstance;
import io.muenchendigital.digiwf.service.instance.domain.model.ServiceInstanceDetail;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Map between {@link ServiceInstanceTO} and {@link ServiceInstance}
 *
 * @author externer.dl.horn
 */
@Mapper
public interface ServiceInstanceApiMapper {

    List<ServiceInstanceTO> map2TO(List<ServiceInstance> list);

    ServiceInstanceDetailTO map2TO(ServiceInstanceDetail instanceDetail);

}
