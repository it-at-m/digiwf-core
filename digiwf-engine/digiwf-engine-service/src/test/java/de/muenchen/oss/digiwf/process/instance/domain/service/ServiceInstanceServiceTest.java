package de.muenchen.oss.digiwf.process.instance.domain.service;

import de.muenchen.oss.digiwf.jsonschema.domain.service.JsonSchemaService;
import de.muenchen.oss.digiwf.process.config.domain.service.ProcessConfigService;
import de.muenchen.oss.digiwf.process.instance.domain.mapper.HistoryTaskMapper;
import de.muenchen.oss.digiwf.process.instance.domain.mapper.ServiceInstanceMapper;
import de.muenchen.oss.digiwf.process.instance.domain.model.ServiceInstance;
import de.muenchen.oss.digiwf.process.instance.infrastructure.entity.ServiceInstanceEntity;
import de.muenchen.oss.digiwf.process.instance.infrastructure.repository.ProcessInstanceInfoRepository;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstanceQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ServiceInstanceServiceTest {

    private final HistoryService historyService = mock(HistoryService.class);
    private final ProcessConfigService processConfigService = mock(ProcessConfigService.class);
    private final ServiceInstanceAuthService serviceInstanceAuthService = mock(ServiceInstanceAuthService.class);
    private final RuntimeService runtimeService = mock(RuntimeService.class);
    private final ProcessInstanceInfoRepository processInstanceInfoRepository = mock(ProcessInstanceInfoRepository.class);
    private final HistoryTaskMapper historyTaskMapper = mock(HistoryTaskMapper.class);
    private final JsonSchemaService jsonSchemaService = mock(JsonSchemaService.class);
    private final ServiceInstanceDataService serviceInstanceDataService = mock(ServiceInstanceDataService.class);
    private final ServiceInstanceMapper serviceInstanceMapper = Mappers.getMapper(ServiceInstanceMapper.class);
    private ServiceInstanceService serviceInstanceService;

    @BeforeEach
    void setup() {
        serviceInstanceService = new ServiceInstanceService(historyService, processConfigService, serviceInstanceAuthService, runtimeService, processInstanceInfoRepository, serviceInstanceMapper, historyTaskMapper, jsonSchemaService, serviceInstanceDataService);
    }

    @Test
    void test_getRootProcessInstanceReturnsRootInstance() {
        // Arrange
        final String rootInstanceId = "rootInstanceId";
        final String instanceId = "callActivityInstanceId";

        final ProcessInstance instance = mock(ProcessInstance.class);
        final ServiceInstanceEntity entity = ServiceInstanceEntity.builder()
                .id(UUID.randomUUID().toString())
                .instanceId(rootInstanceId)
                .definitionName("exampleDefinitionName")
                .definitionKey("exampleDefinitionKey")
                .build();

        when(runtimeService.createProcessInstanceQuery()).thenReturn(mock(ProcessInstanceQuery.class));
        when(runtimeService.createProcessInstanceQuery().processInstanceId(instanceId)).thenReturn(mock(ProcessInstanceQuery.class));
        when(runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult()).thenReturn(instance);
        when(instance.getRootProcessInstanceId()).thenReturn(rootInstanceId);
        when(processInstanceInfoRepository.findByInstanceId(rootInstanceId)).thenReturn(Optional.of(entity));

        // Act
        final ServiceInstance result = serviceInstanceService.getRootProcessInstance(instanceId);

        // Assert
        assertThat(result)
                .isNotNull()
                .hasFieldOrPropertyWithValue("instanceId", rootInstanceId)
                .hasFieldOrPropertyWithValue("definitionName", "exampleDefinitionName")
                .hasFieldOrPropertyWithValue("definitionKey", "exampleDefinitionKey");
    }

    @Test
    void test_getRootProcessInstanceReturnsInstanceIfTheInstanceHasNoRootInstance() {
        // Arrange
        final String instanceId = "processInstanceId";

        final ProcessInstance instance = mock(ProcessInstance.class);
        final ServiceInstanceEntity entity = ServiceInstanceEntity.builder()
                .id(UUID.randomUUID().toString())
                .instanceId(instanceId)
                .definitionName("exampleDefinitionName")
                .definitionKey("exampleDefinitionKey")
                .build();

        when(runtimeService.createProcessInstanceQuery()).thenReturn(mock(ProcessInstanceQuery.class));
        when(runtimeService.createProcessInstanceQuery().processInstanceId(instanceId)).thenReturn(mock(ProcessInstanceQuery.class));
        when(runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult()).thenReturn(instance);
        when(instance.getRootProcessInstanceId()).thenReturn(instanceId);
        when(processInstanceInfoRepository.findByInstanceId(instanceId)).thenReturn(Optional.of(entity));

        // Act
        final ServiceInstance result = serviceInstanceService.getRootProcessInstance(instanceId);

        // Assert
        assertThat(result)
                .isNotNull()
                .hasFieldOrPropertyWithValue("instanceId", instanceId)
                .hasFieldOrPropertyWithValue("definitionName", "exampleDefinitionName")
                .hasFieldOrPropertyWithValue("definitionKey", "exampleDefinitionKey");
    }

    @Test
    void test_getRootProcessInstanceThrowsExceptionIfInstanceDoesNotExist() {
        // Arrange
        final String instanceId = "nonExistingInstanceId";

        final ProcessInstance instance = mock(ProcessInstance.class);

        when(runtimeService.createProcessInstanceQuery()).thenReturn(mock(ProcessInstanceQuery.class));
        when(runtimeService.createProcessInstanceQuery().processInstanceId(instanceId)).thenReturn(mock(ProcessInstanceQuery.class));
        when(runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult()).thenReturn(null);

        assertThatThrownBy(() -> serviceInstanceService.getRootProcessInstance(instanceId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No process instance found for id: " + instanceId);
    }

}
