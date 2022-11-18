package io.muenchendigital.digiwf.humantask.domain.mapper;

import io.muenchendigital.digiwf.humantask.domain.model.ActRuTask;
import io.muenchendigital.digiwf.humantask.infrastructure.entity.ActRuTaskEntity;
import org.mapstruct.Mapper;

@Mapper
public interface ActRuTaskMapper {
    default ActRuTask mapModel(final ActRuTaskEntity entity) {
        return ActRuTask.builder()
                .id(entity.getId())
                .assignee(entity.getAssignee())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .followUpDate(entity.getFollowUpDate())
                .build();
    }
}
