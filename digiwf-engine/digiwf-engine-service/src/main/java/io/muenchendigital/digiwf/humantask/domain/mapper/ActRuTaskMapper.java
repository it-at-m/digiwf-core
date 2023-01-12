package io.muenchendigital.digiwf.humantask.domain.mapper;

import io.muenchendigital.digiwf.humantask.domain.model.ActRuTask;
import io.muenchendigital.digiwf.humantask.infrastructure.entity.camunda.ActRuTaskEntity;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

//@Component
//@AllArgsConstructor
@Mapper(
        uses = {TaskInfoMapper.class}
)
public interface ActRuTaskMapper {
    List<ActRuTask> map2Model(List<ActRuTaskEntity> list);
@Mapping(source = "taskInfoEntity", target = "taskInfo")
    ActRuTask map2Model(ActRuTaskEntity entity);
}
