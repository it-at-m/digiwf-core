package io.muenchendigital.digiwf.task.service.adapter.in.rest.impl;

import io.muenchendigital.digiwf.task.service.adapter.in.rest.mapper.TaskMapper;
import io.muenchendigital.digiwf.task.service.application.port.in.RetrieveTasksForUser;
import io.muenchendigital.digiwf.task.service.domain.PagingAndSorting;
import io.muenchendigital.digiwf.task.service.application.port.in.rest.api.TasksApiDelegate;
import io.muenchendigital.digiwf.task.service.application.port.in.rest.model.PageOfTasksTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Tasks API delegate calling the Retrieve Tasks Use Case.
 */
@Component
@RequiredArgsConstructor
public class TasksApiDelegateImpl implements TasksApiDelegate {

  private final TaskMapper taskMapper;
  private final RetrieveTasksForUser retrieveTasksForUser;

  @Override
  public ResponseEntity<PageOfTasksTO> getCurrentUserTasks(Integer page, Integer size, String query, LocalDate followUp, String sort) {
    var pagingAndSorting = new PagingAndSorting(page, size, sort);
    var result = retrieveTasksForUser.getTasksForCurrentUser(query, followUp, pagingAndSorting);
    return ok(taskMapper.toSchemaTO(result));
  }

  @Override
  public ResponseEntity<PageOfTasksTO> getAssignedGroupTasks(Integer page, Integer size, String query, String sort) {
    var pagingAndSorting = new PagingAndSorting(page, size, sort);
    var result = retrieveTasksForUser.getAssignedTasksForCurrentUserGroup(query, pagingAndSorting);
    return ok(taskMapper.toSchemaTO(result));
  }

  @Override
  public ResponseEntity<PageOfTasksTO> getUnassignedGroupTasks(Integer page, Integer size, String query, String sort) {
    var pagingAndSorting = new PagingAndSorting(page, size, sort);
    var result = retrieveTasksForUser.getUnassignedTasksForCurrentUserGroup(query, pagingAndSorting);
    return ok(taskMapper.toSchemaTO(result));
  }
}
