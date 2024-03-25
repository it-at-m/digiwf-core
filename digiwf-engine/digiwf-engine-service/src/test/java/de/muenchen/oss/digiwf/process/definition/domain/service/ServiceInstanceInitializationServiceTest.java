package de.muenchen.oss.digiwf.process.definition.domain.service;

import de.muenchen.oss.digiwf.process.config.domain.model.ProcessConfig;
import de.muenchen.oss.digiwf.process.config.domain.service.ProcessConfigService;
import de.muenchen.oss.digiwf.process.instance.domain.model.ServiceInstance;
import de.muenchen.oss.digiwf.process.instance.domain.service.ServiceInstanceService;
import de.muenchen.oss.digiwf.process.instance.process.properties.S3Properties;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceInstanceInitializationServiceTest {


    private final ServiceInstance serviceInstance = mock(ServiceInstance.class);
    private final ProcessConfig processConfig = mock(ProcessConfig.class);
    @Mock
    private RepositoryService repositoryService;
    @Mock
    private RuntimeService runtimeService;
    @Mock
    private ServiceInstanceService serviceInstanceService;
    @Mock
    private ProcessConfigService processConfigService;
    @Mock
    private S3Properties s3Properties;
    @InjectMocks
    private ServiceInstanceInitializationService serviceInstanceInitializationService;

    @BeforeEach
    void setUp() {
        when(serviceInstanceService.creatServiceInstance(any(), any())).thenReturn(serviceInstance);
        when(serviceInstance.getId()).thenReturn("serviceInstanceId");
        when(processConfigService.getProcessConfig(any())).thenReturn(Optional.of(processConfig));
        when(s3Properties.getTopic()).thenReturn("s3Topic");
        when(s3Properties.getHttpAPI()).thenReturn("s3HttpAPI");

        ProcessDefinition processDefinition = mock(ProcessDefinition.class);
        when(processDefinition.getName()).thenReturn("processDefinitionName");

        ProcessDefinitionQuery query = mock(ProcessDefinitionQuery.class);
        when(repositoryService.createProcessDefinitionQuery()).thenReturn(query);
        when(query.latestVersion()).thenReturn(query);
        when(query.processDefinitionKey(any())).thenReturn(query);
        when(query.singleResult()).thenReturn(processDefinition);
    }

    @Test
    public void testInitializeInstance() {

        final Map<String, Object> currentVariables = new HashMap<>();
        final String key = "key";
        final String processDefinitionName = "processDefinitionName";
        final String processInstanceId = "processInstanceId";

        serviceInstanceInitializationService.initializeInstance(processInstanceId, "key", currentVariables);

        verify(serviceInstanceService).creatServiceInstance(processDefinitionName, key);
        verify(serviceInstanceService).updateInstanceId(anyString(), eq(processInstanceId));
        verify(runtimeService, times(2)).setVariables(eq(processInstanceId), anyMap());
    }

}
