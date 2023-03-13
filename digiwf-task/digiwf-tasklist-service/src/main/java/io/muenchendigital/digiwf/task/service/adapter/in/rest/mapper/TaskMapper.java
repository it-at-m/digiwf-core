package io.muenchendigital.digiwf.task.service.rest.mapper;

import io.holunda.polyflow.view.Task;
import io.muenchendigital.digiwf.task.service.rest.model.TaskTO;
import io.muenchendigital.digiwf.task.service.rest.model.TaskWithDetailsTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = DateMapper.class)
public interface TaskMapper {

    @Mapping(target = "processName", source = "sourceReference.name")
    TaskTO to(Task task);

    @Mapping(target = "processName", source = "task.sourceReference.name")
    @Mapping(target = "processInstanceId", source = "task.sourceReference.instanceId")
    @Mapping(target = "variables", source = "task.payload")
    @Mapping(target = "schemaRef", source = "schemaRef")
    TaskWithDetailsTO to(Task task, String schemaRef);
}
