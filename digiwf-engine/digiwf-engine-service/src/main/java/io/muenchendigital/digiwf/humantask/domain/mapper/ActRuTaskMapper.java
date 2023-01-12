package io.muenchendigital.digiwf.humantask.domain.mapper;

import io.muenchendigital.digiwf.humantask.domain.model.ActRuTask;
import io.muenchendigital.digiwf.humantask.infrastructure.entity.camunda.ActRuTaskEntity;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ActRuTaskMapper {
    private TaskInfoMapper taskInfoMapper;
    public List<ActRuTask> map2Model(List<ActRuTaskEntity> list) {
        return list.stream().map(this::map2Model).collect(Collectors.toList());
    }
    public ActRuTask map2Model(ActRuTaskEntity entity) {
        return ActRuTask.builder()
                .id(entity.getId())
                .assignee(entity.getAssignee())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                // maybe add ActRuIdentityLink
                .taskInfo(taskInfoMapper.map2Model(entity.getTaskInfoEntity()))
                .build();
    }
}
