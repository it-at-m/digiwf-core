package de.muenchen.oss.digiwf.task.listener;

import de.muenchen.oss.digiwf.task.TaskManagementProperties;
import de.muenchen.oss.digiwf.task.TaskVariables;
import io.holunda.camunda.taskpool.api.task.*;
import io.holunda.polyflow.taskpool.collector.task.TaskEventCollectorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.history.event.HistoricIdentityLinkLogEventEntity;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

import static io.holunda.camunda.bpm.data.CamundaBpmData.writer;


/**
 * Task listener invoked on change of assignee, making sure that no assignment information is ever stored
 * in the process engine.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class AssignmentAssignTaskListener {
  private final TaskManagementProperties.AssignmentProperties properties;
  private final ProcessEngineConfigurationImpl processEngineServices;

  @Order(TaskEventCollectorService.ORDER - 1000) // be before polyflow
  @EventListener(condition = "#task.eventName.equals('assignment')")
  public AssignTaskCommand taskAssigned(final DelegateTask task) {

    if (properties.isShadow()) {
      val assignee = Optional.ofNullable(task.getAssignee()).filter(s -> !s.isEmpty()).orElse(null);
      val writer = writer(task);
      if (properties.isLocal()) {
        log.debug("Shadowing assignment information for task {} in local variable: {}", task.getId(), assignee);
        writer.setLocal(TaskVariables.TASK_ASSIGNEE, assignee);
      } else {
        log.debug("Shadowing assignment information for task {} in global variable: {}", task.getId(), assignee);
        writer.set(TaskVariables.TASK_ASSIGNEE, assignee);
      }

      if (properties.isDelete()) {
        log.debug("Deleting assignment information from task attributes {}", task.getId());
        task.setAssignee(null);
      }

      // emitting an event in event listener will cause the collector to pick it up.
      // manual assignment
      return new AssignTaskCommand(
          task.getId(),
          EngineTaskCommandSorterKt.ORDER_TASK_ASSIGNMENT,
          CamundaTaskEventType.ASSIGN,
          assignee);
    }
    // skip eventing
    return null;
  }


  @Order(TaskEventCollectorService.ORDER - 1000) // be before polyflow
  @EventListener(condition = "#historic.eventType.equals('add-identity-link') || #historic.eventType.equals('delete-identity-link')")
  public UpdateAssignmentTaskCommand taskCandidatesChanged(final HistoricIdentityLinkLogEventEntity historic) {
    log.info("Historic id link change: {}", historic);
    if (properties.isShadow()) {
      if (historic.getType().equals("candidate") && historic.getTaskId() != null) {
        val writer = writer(processEngineServices.getTaskService(), historic.getTaskId());

        val candidates = TaskUtil.getTaskCandidates(processEngineServices.getTaskService(), historic.getTaskId());
        val candidateUsers = TaskUtil.getCandidateUsers(candidates);
        val candidateGroups = TaskUtil.getCandidateGroups(candidates);
        val lowerCaseCandidateGroups = TaskUtil.toLowerCase(candidateGroups);

        if (historic.getOperationType().equals("add")) {
          if (historic.getUserId() != null) {
            val userId = historic.getUserId();
            candidateUsers.add(userId);
            if (properties.isLocal()) {
              writer.setLocal(TaskVariables.TASK_CANDIDATE_USERS, candidateUsers);
            } else {
              writer.set(TaskVariables.TASK_CANDIDATE_USERS, candidateUsers);
            }
            return new AddCandidateUsersCommand(historic.getTaskId(), Collections.singleton(userId));
          } else if (historic.getGroupId() != null) {
            val groupId = historic.getGroupId();
            lowerCaseCandidateGroups.add(groupId.toLowerCase());
            if (properties.isLocal()) {
              writer.setLocal(TaskVariables.TASK_CANDIDATE_GROUPS, lowerCaseCandidateGroups);
            } else {
              writer.set(TaskVariables.TASK_CANDIDATE_GROUPS, lowerCaseCandidateGroups);
            }
            return new AddCandidateGroupsCommand(historic.getTaskId(), Collections.singleton(historic.getGroupId()));
          }
        } else if (historic.getOperationType().equals("delete")) {
          if (historic.getUserId() != null) {
            val userId = historic.getUserId();
            candidateUsers.remove(userId);
            if (properties.isLocal()) {
              writer.setLocal(TaskVariables.TASK_CANDIDATE_USERS, candidateUsers);
            } else {
              writer.set(TaskVariables.TASK_CANDIDATE_USERS, candidateUsers);
            }
            return new DeleteCandidateUsersCommand(historic.getTaskId(), Collections.singleton(historic.getUserId()));
          } else if (historic.getGroupId() != null) {
            val groupId = historic.getGroupId();
            lowerCaseCandidateGroups.remove(groupId.toLowerCase());
            if (properties.isLocal()) {
              writer.setLocal(TaskVariables.TASK_CANDIDATE_GROUPS, lowerCaseCandidateGroups);
            } else {
              writer.set(TaskVariables.TASK_CANDIDATE_GROUPS, lowerCaseCandidateGroups);
            }
            return new DeleteCandidateGroupsCommand(historic.getTaskId(), Collections.singleton(historic.getGroupId()));
          }
        }
      }
    }

    // skip everything
    return null;
  }
}
