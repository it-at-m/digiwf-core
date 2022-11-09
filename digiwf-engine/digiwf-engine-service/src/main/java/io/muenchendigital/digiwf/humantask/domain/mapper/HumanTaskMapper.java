package io.muenchendigital.digiwf.humantask.domain.mapper;

import io.muenchendigital.digiwf.humantask.domain.model.HumanTask;
import io.muenchendigital.digiwf.humantask.domain.model.HumanTaskDetail;
import io.muenchendigital.digiwf.humantask.domain.model.TaskInfo;
import org.camunda.bpm.engine.task.Task;
import org.mapstruct.Mapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface HumanTaskMapper {

    default HumanTask map2Model(
            final Task task,
            final TaskInfo taskInfoEntity) {

        return HumanTask.builder()
                .id(task.getId())
                .assignee(task.getAssignee())
                .assigneeFormatted(taskInfoEntity.getAssignee())
                .creationTime(task.getCreateTime())
                .name(task.getName())
                .description(taskInfoEntity.getDescription())
                .followUpDate(this.mapDate(task.getFollowUpDate()))
                .processName(taskInfoEntity.getDefinitionName() != null ? taskInfoEntity.getDefinitionName() : "")
                .build();
    }

    default HumanTaskDetail map2Model(
            final Task task,
            final TaskInfo taskInfoEntity,
            final Map<String, Object> variables
    ) {
        return HumanTaskDetail.builder()
                .id(task.getId())
                .assignee(task.getAssignee())
                .assigneeFormatted(taskInfoEntity.getAssignee())
                .creationTime(task.getCreateTime())
                .name(task.getName())
                .description(taskInfoEntity.getDescription())
                .processName(taskInfoEntity.getDefinitionName() != null ? taskInfoEntity.getDefinitionName() : "")
                .processInstanceId(task.getProcessInstanceId())
                .followUpDate(this.mapDate(task.getFollowUpDate()))
                .variables(variables)
                .build();
    }

    default String mapDate(final Date date) {
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return Optional.ofNullable(date)
                .map(dateFormat::format)
                .orElse("");
    }
}
