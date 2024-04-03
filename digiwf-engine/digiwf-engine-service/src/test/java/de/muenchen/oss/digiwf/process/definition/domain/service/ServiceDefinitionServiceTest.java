package de.muenchen.oss.digiwf.process.definition.domain.service;

import de.muenchen.oss.digiwf.process.definition.domain.mapper.ServiceDefinitionMapper;
import de.muenchen.oss.digiwf.process.definition.domain.model.ServiceDefinition;
import de.muenchen.oss.digiwf.process.instance.domain.service.ServiceInstanceService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.repository.DeleteProcessDefinitionsBuilder;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Answers;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

public class ServiceDefinitionServiceTest {

    private final RepositoryService repositoryService = mock(RepositoryService.class, withSettings().defaultAnswer(Answers.RETURNS_DEEP_STUBS));
    private final RuntimeService runtimeService = mock(RuntimeService.class, withSettings().defaultAnswer(Answers.RETURNS_DEEP_STUBS));
    private final HistoryService historyService = mock(HistoryService.class, withSettings().defaultAnswer(Answers.RETURNS_DEEP_STUBS));
    private final ServiceInstanceService serviceInstanceService = mock(ServiceInstanceService.class);
    private final ServiceDefinitionMapper serviceDefinitionMapper = Mappers.getMapper(ServiceDefinitionMapper.class);

    private ServiceDefinitionService unitToTest;

    private final static String DEFINITION_KEY = "key";

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

    @Test
    void test_getProcessDefinitionsWithInstanceInfoByKey() {
        var now = Instant.now();

        ProcessDefinition pd1 = mock(ProcessDefinition.class);
        when(pd1.getId()).thenReturn("1");
        ProcessDefinition pd2 = mock(ProcessDefinition.class);
        when(pd2.getId()).thenReturn("2");

        when(repositoryService
                .createProcessDefinitionQuery()
                .active()
                .processDefinitionKey(DEFINITION_KEY)
                .latestVersion()
                .singleResult()).thenReturn(pd2);

        var query = mock(ProcessDefinitionQuery.class);
        when(repositoryService
                .createProcessDefinitionQuery()
                .active()
                .processDefinitionKey(DEFINITION_KEY)
                .orderByProcessDefinitionVersion()
                .asc()).thenReturn(query);
        when(query.list()).thenReturn(List.of(pd1, pd2));

        when(runtimeService.createProcessInstanceQuery()
                .active()
                .processDefinitionId(pd1.getId())
                .count()).thenReturn(1L);
        when(runtimeService.createProcessInstanceQuery()
                .active()
                .processDefinitionId(pd2.getId())
                .count()).thenReturn(2L);

        var hpi1 = mock(HistoricProcessInstance.class);
        when(hpi1.getStartTime()).thenReturn(Date.from(now));
        when(historyService
                .createHistoricProcessInstanceQuery()
                .processDefinitionId(pd1.getId())
                .list()).thenReturn(Collections.singletonList(hpi1));

        var hpi2a = mock(HistoricProcessInstance.class);
        when(hpi2a.getStartTime()).thenReturn(Date.from(now));
        var hpi2b = mock(HistoricProcessInstance.class);
        when(hpi2b.getStartTime()).thenReturn(Date.from(now.minus(Duration.ofDays(10))));
        when(historyService
                .createHistoricProcessInstanceQuery()
                .processDefinitionId(pd2.getId())
                .list()
                ).thenReturn(List.of(hpi2a, hpi2b));

        var result = unitToTest.getProcessDefinitionsWithInstanceInfoByKey(DEFINITION_KEY);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).processDefinitionId()).isEqualTo(pd1.getId());
        assertThat(result.get(0).instanceCount()).isEqualTo(1);
        assertThat(result.get(0).newestProcessInstanceStartTime()).isEqualTo(Date.from(now));

        assertThat(result.get(1).processDefinitionId()).isEqualTo(pd2.getId());
        assertThat(result.get(1).instanceCount()).isEqualTo(2);
        assertThat(result.get(1).newestProcessInstanceStartTime()).isEqualTo(Date.from(now));

    }

    @Test
    void test_deleteDefinitions() {
        var pdIds = new String[] { "1", "2" };

        var builderMock = mock(DeleteProcessDefinitionsBuilder.class);
        when(repositoryService
                .deleteProcessDefinitions()
                .byIds(pdIds)).thenReturn(builderMock);
        when(builderMock.cascade()).thenReturn(builderMock);

        unitToTest.deleteDefinitions(false, pdIds);

        verify(builderMock).delete();

        unitToTest.deleteDefinitions(true, pdIds);

        verify(builderMock).cascade();

    }

    @Test
    void test_createAutomaticMigrationAndRun_notSameKey() {
        ProcessDefinition pd1 = mock(ProcessDefinition.class);
        when(pd1.getId()).thenReturn("1");
        when(pd1.getKey()).thenReturn(DEFINITION_KEY);
        ProcessDefinition pd2 = mock(ProcessDefinition.class);
        when(pd2.getId()).thenReturn("2");
        when(pd2.getKey()).thenReturn("NOT_SAME_KEY");

        when(repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionId("1").singleResult())
                .thenReturn(pd1);
        when(repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionId("2").singleResult())
                .thenReturn(pd2);

        assertThrows(IllegalArgumentException.class, () -> unitToTest.createAutomaticMigrationAndRun("1", "2"));
    }
}
