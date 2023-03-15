package io.muenchendigital.digiwf.task.service.application.port.out.polyflow;

import io.holunda.polyflow.view.Task;
import io.holunda.polyflow.view.auth.User;
import io.muenchendigital.digiwf.task.service.domain.PageOfTasks;
import io.muenchendigital.digiwf.task.service.domain.PagingAndSorting;

/**
 * Port for searching tasks.
 */
public interface TaskQueryPort {
  PageOfTasks getTasksForCurrentUser(User user, String query, PagingAndSorting pagingAndSorting);

  PageOfTasks getTasksForCurrentUserGroup(User user, String query, boolean includeAssigned, PagingAndSorting pagingAndSorting);

  Task getTaskByIdForCurrentUser(User user, String taskId) throws TaskNotFoundException;
}
