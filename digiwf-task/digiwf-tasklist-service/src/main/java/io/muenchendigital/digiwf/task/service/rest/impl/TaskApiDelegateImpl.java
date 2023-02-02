package io.muenchendigital.digiwf.task.service.rest.impl;

import io.holunda.polyflow.view.query.task.TaskQueryResult;
import io.muenchendigital.digiwf.task.service.query.TaskQueryAdapter;
import io.muenchendigital.digiwf.task.service.rest.api.TasksApiDelegate;
import io.muenchendigital.digiwf.task.service.rest.mapper.TaskMapper;
import io.muenchendigital.digiwf.task.service.rest.model.PageOfTasksPageableSortTO;
import io.muenchendigital.digiwf.task.service.rest.model.PageOfTasksPageableTO;
import io.muenchendigital.digiwf.task.service.rest.model.PageOfTasksTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@Component
@RequiredArgsConstructor
public class TaskApiDelegateImpl implements TasksApiDelegate {

    private final TaskMapper taskMapper;
    private final TaskQueryAdapter taskQueryAdapter;

    @Override
    public ResponseEntity<PageOfTasksTO> getCurrentUserTasks(Integer page, Integer size, String query, String sort) {
        var result = taskQueryAdapter.getTasksForCurrentUser(page, size, query, sort);
        return ok(toPageOfTasks(page, size, sort == null, result));
    }

    @Override
    public ResponseEntity<PageOfTasksTO> getAssignedGroupTasks(Integer page, Integer size, String query, String sort) {
        var result = taskQueryAdapter.getTasksForCurrentUserGroup(page, size, query, sort, true);
        return ok(toPageOfTasks(page, size, sort == null, result));
    }

    @Override
    public ResponseEntity<PageOfTasksTO> getUnassignedGroupTasks(Integer page, Integer size, String query, String sort) {
        var result = taskQueryAdapter.getTasksForCurrentUserGroup(page, size, query, sort, false);
        return ok(toPageOfTasks(page, size, sort == null, result));
    }


    private PageOfTasksTO toPageOfTasks(Integer page, Integer size, Boolean sortRequested, TaskQueryResult result) {
        var totalPages = result.getTotalElementCount() / size;
        var tasks = result.getElements().stream().map(taskMapper::to).collect(Collectors.toList());
        var empty = result.getTotalElementCount() == 0;
        return new PageOfTasksTO()
                .pageable(new PageOfTasksPageableTO()
                        .paged(true)
                        .unpaged(false)
                        .pageNumber(page)
                        .pageSize(size)
                        .sort(new PageOfTasksPageableSortTO()
                                .sorted(sortRequested)
                                .unsorted(!sortRequested)
                                .empty(empty)
                        )
                )
                .page(page)
                .content(tasks)
                .size(size)
                .numberOfElements(tasks.size())
                .totalPages(totalPages)
                .totalElements(result.getTotalElementCount())
                .empty(empty)
                .first(page == 1)
                .last(page == totalPages - 1);
    }
}
