package io.muenchendigital.digiwf.task.service.query;

import io.holunda.polyflow.view.TaskQueryClient;
import io.holunda.polyflow.view.query.task.TaskQueryResult;
import io.holunda.polyflow.view.query.task.TasksForUserQuery;
import io.muenchendigital.digiwf.task.service.auth.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskQueryAdapter {

    private final TaskQueryClient taskQueryClient;
    private final CurrentUserService currentUserService;

    public TaskQueryResult getTasksForCurrentUser(Integer page, Integer size, String query, String sort) {
        // TODO: implement assignment filter
        var currentUser = currentUserService.getCurrentUser();
        var filters = buildFilters(query);
        return taskQueryClient.query(new TasksForUserQuery(
                currentUser,
                page,
                size,
                sanitizeSort(sort),
                filters
        )).join();
    }
    public TaskQueryResult getTasksForCurrentUserGroup(Integer page, Integer size, String query, String sort, boolean includeAssigned) {
        // TODO: implement assignment filter
        var currentUser = currentUserService.getCurrentUser();
        var filters = buildFilters(query);
        return taskQueryClient.query(new TasksForUserQuery(
                currentUser,
                page,
                size,
                sanitizeSort(sort),
                filters
        )).join();
    }

    private List<String> buildFilters(String query) {
        // TODO: implement filtering
        return new ArrayList<>();
    }

    private String sanitizeSort(String sort) {
        if (sort == null) {
            sort = "+createdDate";
        } else {
            if (sort.charAt(0) != '+' || sort.charAt(0) != '-') {
                throw new IllegalArgumentException("Sort argument must start with '+' for ascending or '-' for descending");
            }
        }
        return sort;
    }

}
