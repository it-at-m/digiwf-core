package de.muenchen.oss.digiwf.process.definition.domain.service;

import de.muenchen.oss.digiwf.process.definition.domain.mapper.ServiceDefinitionMapper;
import de.muenchen.oss.digiwf.process.definition.domain.model.ServiceDefinition;
import de.muenchen.oss.digiwf.process.instance.domain.service.ServiceInstanceService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Answers;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

public class ServiceDefinitionServiceTest {

    private final RepositoryService repositoryService = mock(RepositoryService.class, withSettings().defaultAnswer(Answers.RETURNS_DEEP_STUBS));
    private final RuntimeService runtimeService = mock(RuntimeService.class);
    private final ServiceInstanceService serviceInstanceService = mock(ServiceInstanceService.class);
    private final ServiceDefinitionMapper serviceDefinitionMapper = Mappers.getMapper(ServiceDefinitionMapper.class);

    private ServiceDefinitionService unitToTest;

    @BeforeEach
    void setup() {
        unitToTest = new ServiceDefinitionService(repositoryService, runtimeService, serviceDefinitionMapper, serviceInstanceService);
    }

    @Test
    void test_getServiceDefinitions() {

        ProcessDefinition pd1 = mock(ProcessDefinition.class);
        when(pd1.getKey()).thenReturn("service1");
        ProcessDefinition pd2 = mock(ProcessDefinition.class);
        when(pd2.getKey()).thenReturn("service2");
        List<ProcessDefinition> pdList = Arrays.asList(pd1, pd2);

        when(repositoryService.createProcessDefinitionQuery()
                .startableInTasklist()
                .active()
                .latestVersion()
                .list()
        ).thenReturn(pdList);

        List<ServiceDefinition> result = unitToTest.getServiceDefinitions();
        assertThat(result)
                .hasSize(2)
                .extracting(ServiceDefinition::getKey)
                .containsAnyOf("service1", "service2");
    }
}
