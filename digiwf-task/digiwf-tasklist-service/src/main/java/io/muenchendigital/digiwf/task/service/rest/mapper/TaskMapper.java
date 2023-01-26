package io.muenchendigital.digiwf.task.service.rest.mapper;

import io.holunda.polyflow.view.Task;
import io.muenchendigital.digiwf.task.service.rest.model.TaskTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = DateMapper.class)
public interface TaskMapper {

    @Mapping(target = "processName", source = "sourceReference.name")
    TaskTO to(Task task);
}
