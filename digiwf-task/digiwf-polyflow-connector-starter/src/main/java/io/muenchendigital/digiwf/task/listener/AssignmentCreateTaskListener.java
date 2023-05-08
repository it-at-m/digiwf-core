package io.muenchendigital.digiwf.task.listener;

import io.holunda.polyflow.taskpool.collector.task.TaskEventCollectorService;
import io.muenchendigital.digiwf.task.TaskManagementProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.task.IdentityLink;
import org.camunda.bpm.engine.task.IdentityLinkType;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.stream.Collectors;

import static io.holunda.camunda.bpm.data.CamundaBpmData.writer;
import static io.muenchendigital.digiwf.task.TaskVariables.*;

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
        if (properties.isShadow()) {
            val assignee = task.getAssignee();
            val candidateUsers = task.getCandidates().stream().filter(link -> link.getUserId() != null && link.getType().equals(IdentityLinkType.CANDIDATE)).map(IdentityLink::getUserId).collect(Collectors.toList());
            val candidateGroups = task.getCandidates().stream().map(IdentityLink::getGroupId).filter(Objects::nonNull).collect(Collectors.toList());
            val writer = writer(task);
            if (properties.isLocal()) {
                log.debug("Shadowing assignment information for task {} in local variables: {}, {}, {}", task.getId(), assignee, candidateUsers, candidateGroups);
                writer
                        .setLocal(TASK_ASSIGNEE, assignee)
                        .setLocal(TASK_CANDIDATE_USERS, candidateUsers)
                        .setLocal(TASK_CANDIDATE_GROUPS, candidateGroups);
            } else {
                log.debug("Shadowing assignment information for task {} in global variables: {}, {}, {}", task.getId(), assignee, candidateUsers, candidateGroups);
                writer
                        .set(TASK_ASSIGNEE, assignee)
                        .set(TASK_CANDIDATE_USERS, candidateUsers)
                        .set(TASK_CANDIDATE_GROUPS, candidateGroups);
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
