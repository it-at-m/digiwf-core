/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.service.instance.api.resource;

import io.muenchendigital.digiwf.service.instance.api.mapper.ServiceInstanceApiMapper;
import io.muenchendigital.digiwf.service.instance.api.transport.ServiceInstanceDetailTO;
import io.muenchendigital.digiwf.service.instance.api.transport.ServiceInstanceTO;
import io.muenchendigital.digiwf.service.instance.domain.service.ServiceInstanceService;
import io.muenchendigital.digiwf.shared.security.AppAuthenticationProvider;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Rest API to interact with process instances.
 *
 * @author externer.dl.horn
 */
@RestController
@Transactional
@RequestMapping("/rest/service/instance")
@RequiredArgsConstructor
@Tag(name = "ServiceInstanceController", description = "API to interact with service instances")
public class ServiceInstanceController {

    private final ServiceInstanceService processInstanceService;
    private final AppAuthenticationProvider authenticationProvider;

    //Mapper
    private final ServiceInstanceApiMapper serviceInstanceApiMapper;

    /**
     * Get all assigned process instances.
     *
     * @return assigned process instances
     */
    @GetMapping()
    public ResponseEntity<List<ServiceInstanceTO>> getAssignedInstances() {
        val startedInstances = this.processInstanceService.getProcessInstanceByUser(this.authenticationProvider.getCurrentUserId());
        return ResponseEntity.ok(this.serviceInstanceApiMapper.map2TO(startedInstances));
    }

    /**
     * Get detail representation of a process instance.
     *
     * @param id Id of the process instance
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServiceInstanceDetailTO> getProcessInstanceDetail(@PathVariable("id") final String id) {
        val startedInstances = this.processInstanceService.getServiceInstanceDetail(id);
        return ResponseEntity.ok(this.serviceInstanceApiMapper.map2TO(startedInstances));
    }

}
