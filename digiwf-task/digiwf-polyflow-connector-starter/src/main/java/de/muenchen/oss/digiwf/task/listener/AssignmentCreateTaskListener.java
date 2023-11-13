package de.muenchen.oss.digiwf.task.listener;

import de.muenchen.oss.digiwf.task.TaskVariables;
import io.holunda.polyflow.taskpool.collector.task.TaskEventCollectorService;
import de.muenchen.oss.digiwf.task.TaskManagementProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;

import static io.holunda.camunda.bpm.data.CamundaBpmData.reader;
import static io.holunda.camunda.bpm.data.CamundaBpmData.writer;

/**
 * Task listener responsible for shadowing assignment information from task into process variables and deleting it from task attributes.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class AssignmentCreateTaskListener {
    private final TaskManagementProperties.AssignmentProperties properties;

    @PostConstruct
    public void report() {
        log.info("DIGIWF-TASK-ASSIGNMENT-001: \n\tshadow: {}, \n\tlocal: {}, \n\tdelete {}", properties.isShadow(), properties.isLocal(), properties.isDelete());
    }

    @Order(TaskEventCollectorService.ORDER - 1000) // be before polyflow
    @EventListener(condition = "#task.eventName.equals('create')")
    public void taskCreated(final DelegateTask task) {
        val reader = reader(task);
        if (properties.isShadow()) {
            val assignee = Optional.ofNullable(task.getAssignee()).filter(s -> !s.isEmpty()).orElse(null);
            val candidateUsers = TaskUtil.getCandidateUsers(task.getCandidates());
            val candidateGroups = TaskUtil.getCandidateGroups(task.getCandidates());
            val lowerCaseCandidateGroups = TaskUtil.toLowerCase(candidateGroups);
            val writer = writer(task);
            if (properties.isLocal()) {
                if (reader.getLocalOptional(TaskVariables.TASK_ASSIGNEE).isPresent() || reader.getLocalOptional(TaskVariables.TASK_CANDIDATE_USERS).isPresent() || reader.getLocalOptional(TaskVariables.TASK_CANDIDATE_GROUPS).isPresent()) {
                    return;
                }
                log.debug("Shadowing assignment information for task {} in local variables: {}, {}, {}", task.getId(), assignee, candidateUsers, lowerCaseCandidateGroups);
                writer
                        .setLocal(TaskVariables.TASK_ASSIGNEE, assignee)
                        .setLocal(TaskVariables.TASK_CANDIDATE_USERS, candidateUsers)
                        .setLocal(TaskVariables.TASK_CANDIDATE_GROUPS, lowerCaseCandidateGroups);
            } else {
                if (reader.getOptional(TaskVariables.TASK_ASSIGNEE).isPresent() || reader.getOptional(TaskVariables.TASK_CANDIDATE_USERS).isPresent() || reader.getOptional(TaskVariables.TASK_CANDIDATE_GROUPS).isPresent()) {
                    return;
                }
                log.debug("Shadowing assignment information for task {} in global variables: {}, {}, {}", task.getId(), assignee, candidateUsers, lowerCaseCandidateGroups);
                writer
                        .set(TaskVariables.TASK_ASSIGNEE, assignee)
                        .set(TaskVariables.TASK_CANDIDATE_USERS, candidateUsers)
                        .set(TaskVariables.TASK_CANDIDATE_GROUPS, lowerCaseCandidateGroups);
            }
            if (properties.isDelete()) {
                log.debug("Deleting assignment information from task attributes {}", task.getId());
                task.setAssignee(null);
                candidateUsers.forEach(task::deleteCandidateUser);
                candidateGroups.forEach(task::deleteCandidateGroup);
            }
        }
    }

}
