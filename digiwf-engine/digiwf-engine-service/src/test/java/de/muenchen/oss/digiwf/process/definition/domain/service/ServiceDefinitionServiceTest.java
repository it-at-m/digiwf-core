package de.muenchen.oss.digiwf.process.definition.domain.service;

import de.muenchen.oss.digiwf.process.definition.domain.mapper.ServiceDefinitionMapper;
import de.muenchen.oss.digiwf.process.definition.domain.model.ServiceDefinition;
import de.muenchen.oss.digiwf.process.instance.domain.service.ServiceInstanceService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Answers;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

public class ServiceDefinitionServiceTest {

    private final RepositoryService repositoryService = mock(RepositoryService.class);
    private final RuntimeService runtimeService = mock(RuntimeService.class);
    private final HistoryService historyService = mock(HistoryService.class);
    private final ServiceInstanceService serviceInstanceService = mock(ServiceInstanceService.class);
    private final ServiceDefinitionMapper serviceDefinitionMapper = Mappers.getMapper(ServiceDefinitionMapper.class);

    private ServiceDefinitionService unitToTest;

    @BeforeEach
    void setup() {
        unitToTest = new ServiceDefinitionService(repositoryService, runtimeService, historyService, serviceDefinitionMapper, serviceInstanceService);
    }

    @Test
    void test_getServiceDefinitions() {

        ProcessDefinition pd1 = mock(ProcessDefinition.class);
        when(pd1.getKey()).thenReturn("service1");
        ProcessDefinition pd2 = mock(ProcessDefinition.class);
        when(pd2.getKey()).thenReturn("service2");

        AtomicBoolean startableCalled = new AtomicBoolean(false);
        ProcessDefinitionQuery processDefinitionQuery = mock(ProcessDefinitionQuery.class, withSettings().defaultAnswer(Answers.RETURNS_DEEP_STUBS));
        when(repositoryService.createProcessDefinitionQuery())
                .thenReturn(processDefinitionQuery);

        when(processDefinitionQuery
                .startableInTasklist()
        ).then(invocation -> {
            startableCalled.set(true);
            return processDefinitionQuery;
        });

        when(processDefinitionQuery
                .active()
                .latestVersion()
                .list()
        ).then(invocation -> {
            if(startableCalled.getAndSet(false)) {
                return List.of(pd1);
            } else {
                return List.of(pd1, pd2);
            }
        });

        List<ServiceDefinition> resultStartable = unitToTest.getServiceDefinitions(true);

        assertThat(resultStartable)
                .hasSize(1)
                .extracting(ServiceDefinition::getKey)
                .containsExactly("service1");

        List<ServiceDefinition> resultAll = unitToTest.getServiceDefinitions(false);

        assertThat(resultAll)
                .hasSize(2)
                .extracting(ServiceDefinition::getKey)
                .containsExactly("service1", "service2");
    }
}
