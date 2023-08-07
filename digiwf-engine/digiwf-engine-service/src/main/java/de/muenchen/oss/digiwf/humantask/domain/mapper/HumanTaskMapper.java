package de.muenchen.oss.digiwf.humantask.domain.mapper;

import de.muenchen.oss.digiwf.humantask.domain.model.ActRuTask;
import de.muenchen.oss.digiwf.humantask.domain.model.HumanTask;
import de.muenchen.oss.digiwf.humantask.domain.model.HumanTaskDetail;
import de.muenchen.oss.digiwf.humantask.domain.model.TaskInfo;
import lombok.val;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Component
public class HumanTaskMapper {

    public HumanTask map2Model(
            final ActRuTask actRuTask) {
        val taskInfo = Optional.ofNullable(actRuTask.getTaskInfo());
        return HumanTask.builder()
                .id(actRuTask.getId())
                .assignee(actRuTask.getAssignee())
                .assigneeFormatted(taskInfo.isEmpty() ? null : taskInfo.get().getAssignee())
                .creationTime(actRuTask.getCreatedAt())
                .name(actRuTask.getName())
                .description(taskInfo.isEmpty() ? null : taskInfo.get().getDescription())
                .followUpDate(this.mapDate(actRuTask.getFollowUpDate()))
                .processName((taskInfo.isEmpty() || taskInfo.get().getDefinitionName() == null) ? "" : taskInfo.get().getDefinitionName())
                .build();
    }

    public HumanTaskDetail map2Model(
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

    public String mapDate(final Date date) {
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return Optional.ofNullable(date)
                .map(dateFormat::format)
                .orElse("");
    }
}
