package de.muenchen.oss.digiwf.camunda.prometheus;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.repository.ProcessDefinition;

import java.util.Optional;

@RequiredArgsConstructor
public class TaskMetricsProvider implements MetricsProvider {

    private final TaskService taskService;
    private final RepositoryService repositoryService;

    private Gauge openTasksByGroup;
    private Gauge openTasks;

    @Override
    public void updateMetrics() {
        this.taskService.createTaskReport().taskCountByCandidateGroup().forEach(result -> Optional.ofNullable(result.getGroupName())
                .map(taskCountByCandidateGroupResult -> {
                    this.openTasksByGroup.labels(result.getGroupName()).set(result.getTaskCount());
                    return true;
                })
                .orElseGet(() -> {
                    this.openTasksByGroup.labels("nogroup").set(result.getTaskCount());
                    return false;
                }));

        this.repositoryService.createProcessDefinitionQuery().list().stream()
            .map(ProcessDefinition::getKey)
            .distinct()
            .forEach( processDefinitionKey -> {
                this.openTasks.labels(processDefinitionKey, "assigned").set(this.taskService.createTaskQuery().taskAssigned().count());
                this.openTasks.labels(processDefinitionKey, "unassigned").set(this.taskService.createTaskQuery().taskUnassigned().count());
                this.openTasks.labels(processDefinitionKey, "hasCandidateGroups").set(this.taskService.createTaskQuery().withCandidateGroups().count());
                this.openTasks.labels(processDefinitionKey, "hasCandidateUsers").set(this.taskService.createTaskQuery().withCandidateUsers().count());
                this.openTasks.labels(processDefinitionKey, "unassignedWithNoCandidates").set(this.taskService.createTaskQuery().taskUnassigned().withoutCandidateGroups().withoutCandidateUsers().count());
                this.openTasks.labels(processDefinitionKey, "total").set(this.taskService.createTaskQuery().count());
            } );
    }

    @Override
    public void registerMetrics(final CollectorRegistry collectorRegistry) {
        this.openTasksByGroup = Gauge.build()
                .name("camunda_open_tasks_group_assignment")
                .help("Number of open tasks by group.")
                .labelNames("candidateGroup")
                .register(collectorRegistry);

        this.openTasks = Gauge.build()
                .name("camunda_open_tasks")
                .help("Number of open tasks.")
                .labelNames("processDefinitionKey", "status")
                .register(collectorRegistry);
    }

}
