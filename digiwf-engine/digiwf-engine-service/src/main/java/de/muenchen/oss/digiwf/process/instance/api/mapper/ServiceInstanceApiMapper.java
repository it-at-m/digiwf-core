/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.process.instance.api.mapper;

import de.muenchen.oss.digiwf.process.instance.api.transport.ServiceInstanceDetailTO;
import de.muenchen.oss.digiwf.process.instance.api.transport.ServiceInstanceTO;
import de.muenchen.oss.digiwf.process.instance.domain.model.ServiceInstance;
import de.muenchen.oss.digiwf.process.instance.domain.model.ServiceInstanceDetail;
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
    ServiceInstanceTO map2TO(ServiceInstance item);

    ServiceInstanceDetailTO map2TO(ServiceInstanceDetail instanceDetail);

}
