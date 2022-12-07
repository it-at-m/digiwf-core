package io.muenchendigital.digiwf.humantask.domain.service;

import io.muenchendigital.digiwf.humantask.domain.mapper.HumanTaskMapper;
import io.muenchendigital.digiwf.humantask.domain.model.ActRuTask;
import io.muenchendigital.digiwf.humantask.domain.model.HumanTask;
import io.muenchendigital.digiwf.humantask.domain.model.TaskInfo;
import io.muenchendigital.digiwf.jsonschema.domain.service.JsonSchemaService;
import io.muenchendigital.digiwf.legacy.form.domain.service.FormService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.extension.mockito.QueryMocks;
import org.junit.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HumanTaskServiceTest {
    private final HumanTaskDataService humanTaskDataService = mock(HumanTaskDataService.class);
    private final TaskInfoService taskInfoService = mock(TaskInfoService.class);
    private final ActRuTaskService actRuTaskService = mock(ActRuTaskService.class);
    private final FormService formService = mock(FormService.class);
    private final JsonSchemaService jsonSchemaService = mock(JsonSchemaService.class);

    private final TaskService taskService = mock(TaskService.class);
    private final HumanTaskMapper humanTaskMapper = mock(HumanTaskMapper.class);
    //    private final HumanTaskMapper humanTaskMapper = new HumanTaskMapperImpl(); //mock(HumanTaskMapper.class);
    private final HumanTaskService humanTaskService = new HumanTaskService(
            humanTaskDataService,
            taskInfoService,
            actRuTaskService,
            formService,
            jsonSchemaService,
            taskService,
            humanTaskMapper
    );

    private final Task task = mock(Task.class);

    private final String assigneeId = "assigneeId";

    /*
    tests for getTasksForUser
     */
//    @Test
//    public void shouldReturnUserTasksPageWhenTasksExists() {
//        final Pageable pageRequest = PageRequest.of(0, 5);
//        final Date date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
//        final List<ActRuTask> actRuTasks = List.of(
//                new ActRuTask("id-1", assigneeId, "name 1", date, date),
//                new ActRuTask("id-2", assigneeId, "name 2", date, date),
//                new ActRuTask("id-3", assigneeId, "name 3", date, date),
//                new ActRuTask("id-4", assigneeId, "name 4", date, date),
//                new ActRuTask("id-5", assigneeId, "name 5", date, date)
//        );
//        final Map<String, TaskInfo> taskInfos = new HashMap<>();
//        taskInfos.put("id-1", new TaskInfo("id-1", "description", "definitionName", assigneeId, "instanceId"));
//        taskInfos.put("id-2", new TaskInfo("id-2", "description", "definitionName", assigneeId, "instanceId"));
//        taskInfos.put("id-3", new TaskInfo("id-3", "description", "definitionName", assigneeId, "instanceId"));
//        taskInfos.put("id-4", new TaskInfo("id-4", "description", "definitionName", assigneeId, "instanceId"));
//        taskInfos.put("id-5", new TaskInfo("id-5", "description", "definitionName", assigneeId, "instanceId"));
//
//        when(actRuTaskService.getActRuTaskEntityByAssigneeId(assigneeId, pageRequest)).thenReturn(new PageImpl<ActRuTask>(actRuTasks));
//        when(taskInfoService.getTaskInfoMapByTaskIds(List.of("id-1", "id-2", "id-3", "id-4", "id-5"))).thenReturn(taskInfos);
//        when(humanTaskMapper.map2Model(any(), any())).thenAnswer(args -> {
//            ActRuTask actRuTask = args.getArgument(0);
//            TaskInfo taskInfo = args.getArgument(1);
//            return new HumanTask(actRuTask.getId(), actRuTask.getName(), taskInfo.getDescription(), "processName", "processInstanceId", actRuTask.getAssignee(), "assigneeFormatted", "followUpDate", actRuTask.getCreatedAt());
//        });
//
//        final var result = humanTaskService.getTasksForUser(assigneeId, pageRequest);
//        assertEquals(5, result.getTotalElements());
//        assertEquals("id-1", result.getContent().get(0).getId());
//        assertEquals("id-2", result.getContent().get(1).getId());
//        assertEquals("id-3", result.getContent().get(2).getId());
//        assertEquals("id-4", result.getContent().get(3).getId());
//        assertEquals("id-5", result.getContent().get(4).getId());
//    }
//
//    @Test
//    public void shouldReturnEmptyPageIfNoTasksAreAvailableOnThisPage() {
//        final Pageable pageRequest = PageRequest.of(1, 5);
//        final List<ActRuTask> actRuTasks = List.of();
//        final Map<String, TaskInfo> taskInfos = new HashMap<>();
//
//        when(actRuTaskService.getActRuTaskEntityByAssigneeId(assigneeId, pageRequest)).thenReturn(new PageImpl<ActRuTask>(actRuTasks, pageRequest, 3));
//        when(taskInfoService.getTaskInfoMapByTaskIds(List.of())).thenReturn(taskInfos);
//        when(humanTaskMapper.map2Model(any(), any())).thenAnswer(args -> {
//            ActRuTask actRuTask = args.getArgument(0);
//            TaskInfo taskInfo = args.getArgument(1);
//            return new HumanTask(actRuTask.getId(), actRuTask.getName(), taskInfo.getDescription(), "processName", "processInstanceId", actRuTask.getAssignee(), "assigneeFormatted", "followUpDate", actRuTask.getCreatedAt());
//        });
//
//        final var result = humanTaskService.getTasksForUser(assigneeId, pageRequest);
//        assertEquals(3, result.getTotalElements());
//        assertEquals(0, result.getContent().size());
//    }
//
//    @Test
//    public void shouldReturnOnlyHumanTaskIfThereIsATaskInfoForIt() {
//        final Pageable pageRequest = PageRequest.of(0, 5);
//        final Date date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
//        final List<ActRuTask> actRuTasks = List.of(
//                new ActRuTask("id-1", assigneeId, "name 1", date, date),
//                new ActRuTask("id-2", assigneeId, "name 2", date, date),
//                new ActRuTask("id-3", assigneeId, "name 3", date, date),
//                new ActRuTask("id-4", assigneeId, "name 4", date, date),
//                new ActRuTask("id-5", assigneeId, "name 5", date, date)
//        );
//        final Map<String, TaskInfo> taskInfos = new HashMap<>();
//        taskInfos.put("id-1", new TaskInfo("id-1", "description", "definitionName", assigneeId, "instanceId"));
//        taskInfos.put("id-2", new TaskInfo("id-2", "description", "definitionName", assigneeId, "instanceId"));
//        taskInfos.put("id-5", new TaskInfo("id-5", "description", "definitionName", assigneeId, "instanceId"));
//
//        when(actRuTaskService.getActRuTaskEntityByAssigneeId(assigneeId, pageRequest)).thenReturn(new PageImpl<ActRuTask>(actRuTasks));
//        when(taskInfoService.getTaskInfoMapByTaskIds(List.of("id-1", "id-2", "id-3", "id-4", "id-5"))).thenReturn(taskInfos);
//        when(humanTaskMapper.map2Model(any(), any())).thenAnswer(args -> {
//            ActRuTask actRuTask = args.getArgument(0);
//            TaskInfo taskInfo = args.getArgument(1);
//            return new HumanTask(actRuTask.getId(), actRuTask.getName(), taskInfo.getDescription(), "processName", "processInstanceId", actRuTask.getAssignee(), "assigneeFormatted", "followUpDate", actRuTask.getCreatedAt());
//        });
//
//        final var result = humanTaskService.getTasksForUser(assigneeId, pageRequest);
//        assertEquals(5, result.getTotalElements());
//        assertEquals(3, result.getContent().size());
//        assertEquals("id-1", result.getContent().get(0).getId());
//        assertEquals("id-2", result.getContent().get(1).getId());
//        assertEquals("id-5", result.getContent().get(2).getId());
//    }
//
//    /*
//    tests for getOpenGroupTasks
//     */
//    @Test
//    public void shouldReturnTasksOfGroupsFromUser() {
//
//    }
//@Test
//    public void shouldReturnEmptyPageContentIfPageDoesNotExist(){
//
//    }
//
//    /*
//    tests for getAssignedGroupTasks
//     */
//

}
