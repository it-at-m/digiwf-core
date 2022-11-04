/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.digitalwf.service.instance.api.mapper;

import de.muenchen.digitalwf.service.instance.api.transport.ServiceInstanceDetailTO;
import de.muenchen.digitalwf.service.instance.api.transport.ServiceInstanceTO;
import de.muenchen.digitalwf.service.instance.domain.model.ServiceInstance;
import de.muenchen.digitalwf.service.instance.domain.model.ServiceInstanceDetail;
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
