package io.muenchendigital.digiwf.task.listener;

import io.holunda.polyflow.taskpool.collector.task.TaskEventCollectorService;
import io.muenchendigital.digiwf.task.BpmnErrors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.model.bpmn.instance.BoundaryEvent;
import org.camunda.bpm.model.bpmn.instance.ErrorEventDefinition;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static io.holunda.camunda.bpm.data.CamundaBpmData.writer;
import static io.muenchendigital.digiwf.task.TaskVariables.TASK_CANCELABLE;

@Component
@RequiredArgsConstructor
@Slf4j
public class CancelableTaskStatusCreateTaskListener {

  @Order(TaskEventCollectorService.ORDER - 1001) // be before polyflow
  @EventListener(condition = "#task.eventName.equals('create')")
  public void taskCreated(DelegateTask task) {
    writer(task).set(TASK_CANCELABLE, hasAttachedErrorBoundaryEvent(task));
  }

  /**
   * Checks if a user task has at least one error boundary event attached.
   *
   * @param task task to check.
   * @return true, if attached.
   */
  static boolean hasAttachedErrorBoundaryEvent(DelegateTask task) {
    val userTask = task.getBpmnModelElementInstance();
    val boundaryEvents = userTask.getModelInstance().getModelElementsByType(BoundaryEvent.class);
    return boundaryEvents.stream()
        .filter(boundaryEvent -> boundaryEvent.getAttachedTo().getName().equals(userTask.getName())) // attached boundary events
        .map(boundaryEvent -> boundaryEvent.getEventDefinitions().stream().filter(event -> event instanceof ErrorEventDefinition)) // only errors
        .anyMatch(errorEvents -> errorEvents
            .map(event -> (ErrorEventDefinition) event) // cast
            .anyMatch(CancelableTaskStatusCreateTaskListener::matchesErrorDefinition) // matching name
        );
  }

  static boolean matchesErrorDefinition(ErrorEventDefinition errorEventDefinition) {
    return errorEventDefinition.getError().getErrorCode() == null
        || errorEventDefinition.getError().getErrorCode().isBlank()
        || BpmnErrors.DEFAULT_TASK_CANCELLATION_ERROR.equals(errorEventDefinition.getError().getErrorCode());
  }
}
