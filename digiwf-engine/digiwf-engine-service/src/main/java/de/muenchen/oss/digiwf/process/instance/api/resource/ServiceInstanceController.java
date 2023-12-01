/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.process.instance.api.resource;

import de.muenchen.oss.digiwf.process.instance.api.mapper.ServiceInstanceApiMapper;
import de.muenchen.oss.digiwf.process.instance.api.transport.ServiceInstanceDetailTO;
import de.muenchen.oss.digiwf.process.instance.api.transport.ServiceInstanceTO;
import de.muenchen.oss.digiwf.process.instance.domain.service.ServiceInstanceService;
import de.muenchen.oss.digiwf.shared.security.AppAuthenticationProvider;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

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
    public Page<ServiceInstanceTO> getAssignedInstances(
            @RequestParam(value = "page", defaultValue = "0", required = false) @Min(0)  final int page,
            @RequestParam(value = "size", defaultValue = "50", required = false) @Min(1) @Max(50) final int size,
            @RequestParam(value = "query", required = false) @Nullable final String query
    ) {
        val userId = this.authenticationProvider.getCurrentUserId();
        val startedInstances = this.processInstanceService.getProcessInstanceByUser(userId, page, size, query);
        return startedInstances.map(this.serviceInstanceApiMapper::map2TO);
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
