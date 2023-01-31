package io.muenchendigital.digiwf.task.domain.mapper;

import io.muenchendigital.digiwf.task.domain.model.ActRuTask;
import io.muenchendigital.digiwf.task.infrastructure.entity.camunda.ActRuTaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        uses = {TaskInfoMapper.class}
)
public interface ActRuTaskMapper {
    List<ActRuTask> map2Model(List<ActRuTaskEntity> list);

    @Mapping(source = "taskInfoEntity", target = "taskInfo")
    ActRuTask map2Model(ActRuTaskEntity entity);
}
