package io.muenchendigital.digiwf.task;

import io.holunda.camunda.bpm.data.factory.VariableFactory;

import java.util.List;

import static io.holunda.camunda.bpm.data.CamundaBpmData.*;

/**
 * Definition of task variables.
 */
public class TaskVariables {
  /**
   * Task variable containing the JSON schema for the task.
   */
  public static final VariableFactory<String> TASK_SCHEMA_KEY = stringVariable("app_task_schema_key");

  /**
   * Task variable containing the assignee of the task.
   */
  public static final VariableFactory<String> TASK_ASSIGNEE = stringVariable("app_task_assignee");
  /**
   * Task variable containing the candidate users of the task.
   */
  public static final VariableFactory<List<String>> TASK_CANDIDATE_USERS = listVariable("app_task_candidate_users", String.class);
  /**
   * Task variable containing the candidate groups of the task.
   */
  public static final VariableFactory<List<String>> TASK_CANDIDATE_GROUPS = listVariable("app_task_candidate_groups", String.class);

  /**
   * Flag indicating if the task is cancellable.
   */
  public static final VariableFactory<Boolean> TASK_CANCELABLE = booleanVariable("app_task_cancelable");

  //TODO description
  public static final VariableFactory<String> PROCESS_FILE_CONTEXT = stringVariable("app_file_context");
  public static final VariableFactory<String> PROCESS_ASYNC_CONFIG = stringVariable("app_file_s3_async_config");
  public static final VariableFactory<String> PROCESS_SYNC_CONFIG = stringVariable("app_file_s3_sync_config");
  public static final VariableFactory<String> FILE_PATHS = stringVariable("app_file_paths");
  public static final VariableFactory<String> FILE_PATHS_READONLY = stringVariable("app_file_paths_readonly");

}
