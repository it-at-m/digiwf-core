package de.muenchen.oss.digiwf.cleanup.services;

import de.muenchen.oss.digiwf.cleanup.services.calculation.CleanupCalculator;
import de.muenchen.oss.digiwf.process.instance.infrastructure.entity.ServiceInstanceEntity;
import de.muenchen.oss.digiwf.process.instance.infrastructure.repository.ProcessInstanceInfoRepository;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests for RemovaltimeUpdateService.
 *
 * @author martin.dietrich
 */
public class RemovaltimeUpdateServiceTest {

    private RemovaltimeUpdateService service;

    private final ProcessInstanceInfoRepository processInstanceInfoRepository = mock(ProcessInstanceInfoRepository.class);

    private final CleanupCalculator cleanupCalculator = mock(CleanupCalculator.class);

    final Page<ServiceInstanceEntity> pagedInstanceList = mock(Page.class);

    @BeforeEach
    public void init() {
        service = new RemovaltimeUpdateService(processInstanceInfoRepository, cleanupCalculator);
    }

    @Test
    public void testUpdateServiceInstances_WithOneMissingRemovaltime() {

        final String definitionKey = "1234";
        final ServiceInstanceEntity entity = new ServiceInstanceEntity();
        entity.setDefinitionKey(definitionKey);
        entity.setEndTime(new Date());

        // mock instance query
        final String instanceId = "5678";
        final ArrayList<ServiceInstanceEntity> serviceInstanceEntityList = new ArrayList<>();
        serviceInstanceEntityList.add(entity);
        when(pagedInstanceList.iterator()).thenReturn(serviceInstanceEntityList.iterator());
        when(processInstanceInfoRepository.findAll(any(PageRequest.class))).thenReturn(pagedInstanceList);
        when(processInstanceInfoRepository.findByInstanceId(instanceId)).thenReturn(Optional.of(entity));

        // mock definition
        final ProcessDefinition mockedProcessDefinition = Mockito.mock(ProcessDefinition.class);
        when(mockedProcessDefinition.getHistoryTimeToLive()).thenReturn(1);

        // mock calculator
        when(cleanupCalculator.calculateRemovalTime(eq(definitionKey), any(), any())).thenReturn(new Date());

        // method under test
        service.updateServiceInstance(instanceId);

        // then
        final ArgumentCaptor<ServiceInstanceEntity> argument = ArgumentCaptor.forClass(ServiceInstanceEntity.class);
        verify(processInstanceInfoRepository).save(argument.capture());
        assertThat(argument.getValue().getRemovalTime()).isNotNull();

        verify(processInstanceInfoRepository, times(1)).save(entity);
    }

}
