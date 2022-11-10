package io.muenchendigital.digiwf.cleanup.services;

import io.muenchendigital.digiwf.cleanup.services.calculation.CleanupCalculator;
import io.muenchendigital.digiwf.service.instance.infrastructure.entity.ServiceInstanceEntity;
import io.muenchendigital.digiwf.service.instance.infrastructure.repository.ProcessInstanceInfoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service to update removal time of process instance.
 *
 * @author martin.dietrich
 */
@Service
@AllArgsConstructor
@Slf4j
public class RemovaltimeUpdateService {

    private final ProcessInstanceInfoRepository processInstanceInfoRepository;
    private final CleanupCalculator cleanupCalculator;

    public List<String> listUpdateableServiceInstances() {
        final List<ServiceInstanceEntity> instanceList = processInstanceInfoRepository.findAll();
        final List<String> result = new ArrayList<>();
        for (final ServiceInstanceEntity instance : instanceList) {
            if (instance.getRemovalTime() == null) {
                if (cleanupCalculator.canCalculate(instance.getStartTime(), instance.getEndTime())) {
                    result.add(instance.getInstanceId());
                }
            }
        }
        return result;
    }

    public boolean updateServiceInstance(final String instanceId) {
        final Optional<ServiceInstanceEntity> instance = processInstanceInfoRepository.findByInstanceId(instanceId);
        if (instance.isEmpty()) {
            return false;
        }
        updateServiceInstanceEntity(instance.get());
        return true;
    }

    private void updateServiceInstanceEntity(final ServiceInstanceEntity instance){
        log.debug("Updating removaltime of process instance: {}", instance.getInstanceId());
        final Date removaltime = cleanupCalculator.calculateRemovalTime(instance.getDefinitionKey(), instance.getStartTime(), instance.getEndTime());
        instance.setRemovalTime(removaltime);
        processInstanceInfoRepository.save(instance);
        log.info("Updated removaltime of process instance {}", instance.getInstanceId());
    }

}
