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
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

import static io.holunda.camunda.bpm.data.CamundaBpmData.writer;


/**
 * Task listener invoked on change of assignee, candidate user and candidate group making to pass this information
 * via variables or direct commands to Polyflow.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class AssignmentAssignTaskListener {
  private final TaskManagementProperties.AssignmentProperties properties;
  private final ProcessEngineConfigurationImpl processEngineServices;

  /**
   * Reacts on assignment change on the task.
   *
   * @param task task event fired by Camunda engine if assignment is changed.
   * @return assign command sent to polyflow.
   */
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


  /**
   * React on candidate-user / candidate-group change on the task.
   *
   * @param historic historic event containing the assignment information,
   * @return assignment update command.
   */
  @Order(TaskEventCollectorService.ORDER - 1000) // be before polyflow
  @EventListener(condition = "#historic.eventType.equals('add-identity-link') || #historic.eventType.equals('delete-identity-link')")
  public UpdateAssignmentTaskCommand taskCandidatesChanged(final HistoricIdentityLinkLogEventEntity historic) {
    log.info("Historic id link change: {}", historic);
    if (properties.isShadow() && historic.getTaskId() != null) {
      val taskEntity = (TaskEntity)processEngineServices.getTaskService().createTaskQuery().taskId(historic.getTaskId()).singleResult();
      if (historic.getType().equals("candidate") && taskEntity != null) {
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

  /**
   * Catches the task attribute update command indicating just a candidate update (since unchanged is true, no other task attributes
   * where changed or detected as changed by Camunda).
   * <p>
   * Flip the changed property to allow the update command be sent
   * as a dedicated intent by the Polyflow Intent Detector, see {@link io.holunda.polyflow.taskpool.sender.task.accumulator.SimpleEngineTaskCommandIntentDetector}
   * If the "unchanged" command is caught we duplicate it with a "changed" version.
   * By a candidate-user/candidate-group change this will result in 5 commands in total:
   * the one thrown by the method above + 2 unchanged updates + 2 changed updates.
   * Since the updates have high priority than assignment, those two changed will be detected as one own intent and will be projected together with
   * unchanged versions.
   * </p>
   * <p>
   * At the end two commands will be sent to Polyflow: an assignment change and the "changed" attribute update carrying variable changes.
   * </p>
   *
   * @param updateAttributeCommand command resulted from an "echo" of Camunda during update of a task candidate group or user.
   * @return update command as a separate intent.
   */
  @Order(TaskEventCollectorService.ORDER - 1001) // be before polyflow
  @EventListener
  public UpdateAttributeTaskCommand taskCandidatesChanged(final UpdateAttributeTaskCommand updateAttributeCommand) {
    if (updateAttributeCommand.getUnchanged()) {
      return updateAttributeCommand.copy(
          updateAttributeCommand.getId(),
          updateAttributeCommand.getOrder(),
          updateAttributeCommand.getEventName(),
          updateAttributeCommand.getSourceReference(),
          updateAttributeCommand.getTaskDefinitionKey(),
          updateAttributeCommand.getBusinessKey(),
          updateAttributeCommand.getPayload(),
          updateAttributeCommand.getCorrelations(),
          updateAttributeCommand.getEnriched(),
          updateAttributeCommand.getDescription(),
          updateAttributeCommand.getDueDate(),
          updateAttributeCommand.getFollowUpDate(),
          updateAttributeCommand.getName(),
          updateAttributeCommand.getOwner(),
          updateAttributeCommand.getPriority(),
          false // flip to false => the task update is now changed, and we will get two commands (because of two intents), since the "changed"
          // UpdateAttributeCommand is not "projectable" with UpdateAssignmentCommand and will be delivered independently.
      );
    } else {
      return null;
    }
  }

}
