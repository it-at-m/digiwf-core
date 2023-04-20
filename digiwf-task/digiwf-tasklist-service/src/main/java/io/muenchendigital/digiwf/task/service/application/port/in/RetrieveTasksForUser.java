package io.muenchendigital.digiwf.task.service.application.port.in;

import io.muenchendigital.digiwf.task.service.domain.PageOfTasksWithSchema;
import io.muenchendigital.digiwf.task.service.domain.PagingAndSorting;

import java.time.LocalDate;

/**
 * Use case describing the general search and navigation to user's tasks.
 */
public interface RetrieveTasksForUser {
  /**
   * Retrieves a list of unassigned (open) tasks, visible via one of current user's groups.
   *
   * @param query            additional query containing limiting parameters.
   * @param pagingAndSorting information about paging and sorting.
   * @return a page of tasks with schema.
   */
  PageOfTasksWithSchema getUnassignedTasksForCurrentUserGroup(String query, PagingAndSorting pagingAndSorting);

  /**
   * Retrieves a list of tasks assigned to someone, but visible via one of current user's groups.
   *
   * @param query            additional query containing limiting parameters.
   * @param pagingAndSorting information about paging and sorting.
   * @return a page of tasks with schema.
   */
  PageOfTasksWithSchema getAssignedTasksForCurrentUserGroup(String query, PagingAndSorting pagingAndSorting);

  /**
   * Retrieves the list of tasks assigned directly to the current user.
   *
   * @param query            additional query containing limiting parameters.
   * @param followUp         optional date to include the user tasks with follow-up date before this date.
   * @param pagingAndSorting information about paging and sorting.
   * @return a page of tasks with schema.
   */
  PageOfTasksWithSchema getTasksForCurrentUser(String query, LocalDate followUp, PagingAndSorting pagingAndSorting);
}
