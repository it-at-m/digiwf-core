package io.muenchendigital.digiwf.task.service.rest.impl;

import io.muenchendigital.digiwf.task.service.query.TaskQueryAdapter;
import io.muenchendigital.digiwf.task.service.rest.api.TasksApiDelegate;
import io.muenchendigital.digiwf.task.service.rest.mapper.TaskMapper;
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
    public ResponseEntity<PageOfTasksTO> getCurrentUserTasks(Integer page, Integer size, String query) {
        var result = taskQueryAdapter.getTasksForCurrentUser(page, size, query, "created+");
        var tasks = result.getElements().stream().map(taskMapper::to).collect(Collectors.toList());
        return ok(new PageOfTasksTO().page(page).content(tasks).size(size).numberOfElements(result.getTotalElementCount()));
    }

    @Override
    public ResponseEntity<PageOfTasksTO> getAssignedGroupTasks(Integer page, Integer size, String query) {
        var result = taskQueryAdapter.getTasksForCurrentUserGroup(page, size, query, "created+", true);
        var tasks = result.getElements().stream().map(taskMapper::to).collect(Collectors.toList());
        return ok(new PageOfTasksTO().page(page).content(tasks).size(size).numberOfElements(result.getTotalElementCount()));
    }

    @Override
    public ResponseEntity<PageOfTasksTO> getUnassignedGroupTasks(Integer page, Integer size, String query) {
        var result = taskQueryAdapter.getTasksForCurrentUserGroup(page, size, query, "created+", true);
        var tasks = result.getElements().stream().map(taskMapper::to).collect(Collectors.toList());
        return ok(new PageOfTasksTO().page(page).content(tasks).size(size).numberOfElements(result.getTotalElementCount()));
    }
}
