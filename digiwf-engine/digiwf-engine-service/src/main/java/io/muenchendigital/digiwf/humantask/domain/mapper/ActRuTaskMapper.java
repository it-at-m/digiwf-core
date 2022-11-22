package io.muenchendigital.digiwf.humantask.domain.mapper;

import io.muenchendigital.digiwf.humantask.domain.model.ActRuTask;
import io.muenchendigital.digiwf.humantask.infrastructure.entity.ActRuTaskEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ActRuTaskMapper {
    List<ActRuTask> map2Model(List<ActRuTaskEntity> list);
    ActRuTask map2Model(ActRuTaskEntity entity);
}
