package de.muenchen.oss.digiwf.camunda.prometheus;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class MetricsAutoConfiguration {

    @Bean
    public MetricsReporter executionEventReporter(RepositoryService repositoryService) {
        return new ExecutionEventReporter(repositoryService);
    }

    @Bean
    public MetricsReporter historyEventReporter() {
        return new HistoryEventReporter();
    }

    @Bean
    public MetricsReporter taskEventReporter(RepositoryService repositoryService) {
        return new TaskEventReporter(repositoryService);
    }

    @Bean
    @ConditionalOnProperty(prefix = "digiwf.prometheus.process-engine.providers", name = "incident")
    public MetricsProvider incidentMetricsProvider(RuntimeService runtimeService) {
        return new IncidentMetricsProvider(runtimeService);
    }

    @Bean
    @ConditionalOnProperty(prefix = "digiwf.prometheus.process-engine.providers", name = "job")
    public MetricsProvider jobMetricsProvider(ManagementService managementService) {
        return new JobMetricsProvider(managementService);
    }

    @Bean
    @ConditionalOnProperty(prefix = "digiwf.prometheus.process-engine.providers", name = "process")
    public MetricsProvider processMetricsProvider(RuntimeService runtimeService, RepositoryService repositoryService) {
        return new ProcessMetricsProvider(runtimeService, repositoryService);
    }

    @Bean
    @ConditionalOnProperty(prefix = "digiwf.prometheus.process-engine.providers", name = "task")
    public MetricsProvider taskMetricsProvider(TaskService taskService) {
        return new TaskMetricsProvider(taskService);
    }
}
