package de.muenchen.oss.digiwf.camunda.prometheus;

import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.springframework.context.annotation.Bean;


public class MetricsConfiguration {

    @Bean
    public MetricsProvider taskMetricsProvider(final TaskService taskService) {
        return new TaskMetricsProvider(taskService);
    }

    @Bean
    public MetricsProvider processMetricsProvider(final RuntimeService runtimeService, final RepositoryService repositoryService) {
        return new ProcessMetricsProvider(runtimeService, repositoryService);
    }

    @Bean
    public MetricsProvider jobMetricsProvider(final ManagementService managementService) {
        return new JobMetricsProvider(managementService);
    }

    @Bean
    public MetricsProvider incidentMetricsProvider(final RuntimeService runtimeService) {
        return new IncidentMetricsProvider(runtimeService);
    }

    @Bean
    public MetricsProvider fniAndEdeMetricsProvider(final ManagementService managementService) {
        return new FniAndEdeMetricsProvider(managementService);
    }

}
