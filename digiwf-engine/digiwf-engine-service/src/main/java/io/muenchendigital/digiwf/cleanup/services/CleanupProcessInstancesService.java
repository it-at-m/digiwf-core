package io.muenchendigital.digiwf.cleanup.services;

import io.muenchendigital.digiwf.process.instance.domain.model.ServiceInstance;
import io.muenchendigital.digiwf.process.instance.domain.service.ServiceInstanceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Service to cleanup expired process instances.
 *
 * @author martin.dietrich
 */
@Service
@AllArgsConstructor
@Slf4j
public class CleanupProcessInstancesService {

    private final ServiceInstanceService serviceInstanceService;

    public void cleanup() {
        log.info("Cleaning up expired process instances");
        List<ServiceInstance> instances = serviceInstanceService.getProcessInstanceByRemovalTimeBefore(new Date());

        for (ServiceInstance instance : instances) {
            serviceInstanceService.cleanupInstance(instance.getInstanceId());
        }
        log.info("Cleaned up {} instances", instances.size());
    }
}
