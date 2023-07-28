package de.muenchen.oss.digiwf.humantask.domain.mapper;

import de.muenchen.oss.digiwf.humantask.domain.model.ActRuTask;
import de.muenchen.oss.digiwf.humantask.infrastructure.entity.camunda.ActRuTaskEntity;
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
