package io.muenchendigital.digiwf.task;

import io.holunda.camunda.bpm.data.factory.VariableFactory;

import static io.holunda.camunda.bpm.data.CamundaBpmData.booleanVariable;
import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;

/**
 * Definition of task variables.
 */
public class TaskVariables {
  /**
   * Schema reference for the task.
   */
  public static final VariableFactory<String> TASK_SCHEMA_KEY = stringVariable("app_task_schema_key");

  /**
   * Flag indicating if the task is cancellable.
   */
  public static final VariableFactory<Boolean> TASK_CANCELABLE = booleanVariable("app_task_cancelable");
}
