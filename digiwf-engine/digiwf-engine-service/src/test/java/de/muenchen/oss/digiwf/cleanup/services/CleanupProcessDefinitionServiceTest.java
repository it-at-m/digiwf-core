package de.muenchen.oss.digiwf.cleanup.services;

import de.muenchen.oss.digiwf.process.definition.domain.model.ServiceDefinition;
import de.muenchen.oss.digiwf.process.definition.domain.service.ServiceDefinitionService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CleanupProcessDefinitionServiceTest {

    ServiceDefinitionService serviceDefinitionService = mock(ServiceDefinitionService.class);

    CleanupProcessDefinitionService unitUnderTest = new CleanupProcessDefinitionService(serviceDefinitionService);

    private final static String DEFINITION_KEY = "key";

    @Test
    public void testGetInfoForDefinitionKey() {
        when(serviceDefinitionService.getProcessDefinitionsWithInstanceInfoByKey(DEFINITION_KEY))
                .thenReturn(List.of(
                        new ServiceDefinitionService.ProcessDefinitionWithInstanceInfo("1", 1, false, 1, null),
                        new ServiceDefinitionService.ProcessDefinitionWithInstanceInfo("2", 2, true, 0, null)
                ));

        var result = unitUnderTest.getInfoForDefinitionKey(DEFINITION_KEY);

        assertThat(result).hasSize(2);
    }

    @Test
    public void testRetrieveAllKeys() {
        when(serviceDefinitionService.getServiceDefinitions(anyBoolean())).thenReturn(List.of(
                new ServiceDefinition("1", "key1", "test", "1.0.0"),
                new ServiceDefinition("2", "key2",  "test", "1.0.0")
        ));

        var result = unitUnderTest.retrieveAllKeys();

        assertThat(result).hasSize(2).contains("1", "2");
    }

    @Test
    public void testMigrateAutomatically() {
        when(serviceDefinitionService.getProcessDefinitionsWithInstanceInfoByKey(DEFINITION_KEY))
                .thenReturn(List.of(
                        new ServiceDefinitionService.ProcessDefinitionWithInstanceInfo("1", 1, false, 1, null),
                        new ServiceDefinitionService.ProcessDefinitionWithInstanceInfo("2", 2, true, 1, null)
                ));

        unitUnderTest.migrateAutomatically(DEFINITION_KEY);

        verify(serviceDefinitionService).createAutomaticMigrationAndRun("1", "2");

    }

    @Test
    public void testMigrateAutomaticallyNoLatest() {
        when(serviceDefinitionService.getProcessDefinitionsWithInstanceInfoByKey(DEFINITION_KEY))
                .thenReturn(List.of(
                        new ServiceDefinitionService.ProcessDefinitionWithInstanceInfo("1", 1, false, 1, null),
                        new ServiceDefinitionService.ProcessDefinitionWithInstanceInfo("2", 2, false, 1, null)
                ));

        assertThrows(IllegalStateException.class, () -> unitUnderTest.migrateAutomatically(DEFINITION_KEY));
    }

    @Test
    public void testMigrateAutomaticallyMigrationFailsSilently() {
        when(serviceDefinitionService.getProcessDefinitionsWithInstanceInfoByKey(DEFINITION_KEY))
                .thenReturn(List.of(
                        new ServiceDefinitionService.ProcessDefinitionWithInstanceInfo("1", 1, false, 1, null),
                        new ServiceDefinitionService.ProcessDefinitionWithInstanceInfo("2", 2, true, 1, null)
                ));

        doThrow(new RuntimeException()).when(serviceDefinitionService).createAutomaticMigrationAndRun(anyString(), anyString());

        unitUnderTest.migrateAutomatically(DEFINITION_KEY);
    }

}
