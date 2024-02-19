package de.muenchen.oss.digiwf.camunda.prometheus;

import org.camunda.bpm.engine.*;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MetricsAutoConfiguration {

    @Bean
    public MetricsReporter executionEventReporter(RepositoryService repositoryService) {
        return new ExecutionEventReporter(repositoryService);
    }
    @Bean
    public MetricsProvider fniAndEdeMetricsProvider(ManagementService managementService, RepositoryService repositoryService, HistoryService historyService) {
        return new FniAndEdeMetricsProvider(managementService, repositoryService, historyService);
    }
    @Bean
    public MetricsReporter historyEventReporter() {
        return new HistoryEventReporter();
    }
    @Bean
    public MetricsProvider incidentMetricsProvider(RuntimeService runtimeService, RepositoryService repositoryService) {
        return new IncidentMetricsProvider(runtimeService, repositoryService);
    }
    @Bean
    public MetricsProvider jobMetricsProvider(ManagementService managementService) {
        return new JobMetricsProvider(managementService);
    }
    @Bean
    public MetricsProvider processMetricsProvider(RuntimeService runtimeService, RepositoryService repositoryService) {
        return new ProcessMetricsProvider(runtimeService, repositoryService);
    }
    @Bean
    public MetricsReporter taskEventReporter(RepositoryService repositoryService) {
        return new TaskEventReporter(repositoryService);
    }
    @Bean
    public MetricsProvider taskMetricsProvider(TaskService taskService, RepositoryService repositoryService) {
        return new TaskMetricsProvider(taskService, repositoryService);
    }
}
