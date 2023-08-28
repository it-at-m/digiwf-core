/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.humantask.process;

import io.holunda.camunda.bpm.data.factory.VariableFactory;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;

/**
 * Constants of the digitalwf platform that are used in a task.
 */
public class ProcessTaskConstants {

    /**
     * Key of the description variable.
     */
    @Deprecated
    public static final String TASK_DESCRIPTION_DIGITALWF = "digitalwf_task_description";

    public static final String TASK_DESCRIPTION = "app_task_description";

    public static final String TASK_SCHEMA_KEY = "app_task_schema_key";
    public static final String TASK_TAG_KEY = "app_task_tag";

    public static final String TASK_STATUS_DOKUMENT = "app_task_status_dokument_enabled";

    public static final String FILE_PATHS = "app_file_paths";

    public static final String FILE_PATHS_READONLY = "app_file_paths_readonly";

    public static final VariableFactory<String> TASK_DESCRIPTION_VARIABLE = stringVariable(TASK_DESCRIPTION);
    public static final VariableFactory<String> TASK_TAG_VARIABLE = stringVariable(TASK_TAG_KEY);

    public static final VariableFactory<String> TASK_DESCRIPTION_DIGITALWF_VARIABLE = stringVariable(TASK_DESCRIPTION_DIGITALWF);

    public static final VariableFactory<String> APP_NOTIFICATION_SEND_ASSIGNEE = stringVariable("app_notification_send_assignee");

    public static final VariableFactory<String> APP_NOTIFICATION_SEND_CANDIDATE_USERS = stringVariable("app_notification_send_candidate_users");

    public static final VariableFactory<String> APP_NOTIFICATION_SEND_CANDIDATE_GROUPS = stringVariable("app_notification_send_candidate_groups");

}
