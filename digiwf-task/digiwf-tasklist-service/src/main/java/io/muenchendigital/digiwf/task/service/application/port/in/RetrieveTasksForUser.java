package io.muenchendigital.digiwf.task.service.application.port.in;

import io.muenchendigital.digiwf.task.service.domain.PageOfTasksWithSchema;
import io.muenchendigital.digiwf.task.service.domain.PagingAndSorting;

public interface RetrieveTasksForUser {
  PageOfTasksWithSchema getUnassignedTasksForCurrentUserGroup(String query, PagingAndSorting pagingAndSorting);

  PageOfTasksWithSchema getAssignedTasksForCurrentUserGroup(String query, PagingAndSorting pagingAndSorting);

  PageOfTasksWithSchema getTasksForCurrentUser(String query, PagingAndSorting pagingAndSorting);
}
