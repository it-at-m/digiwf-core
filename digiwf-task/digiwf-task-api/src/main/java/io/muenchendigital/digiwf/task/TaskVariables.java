package io.muenchendigital.digiwf.task;

import io.holunda.camunda.bpm.data.factory.VariableFactory;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;

/**
 * Definition of task variables.
 */
public class TaskVariables {
  public static final VariableFactory<String> APP_SCHEMA_REF = stringVariable("app_task_schema_key");
}
