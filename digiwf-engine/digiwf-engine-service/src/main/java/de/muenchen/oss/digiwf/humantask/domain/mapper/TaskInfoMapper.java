/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.humantask.domain.mapper;

import de.muenchen.oss.digiwf.humantask.domain.model.TaskInfo;
import de.muenchen.oss.digiwf.humantask.infrastructure.entity.TaskInfoEntity;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Map to {@link TaskInfo}
 *
 * @author externer.dl.horn
 */
@Mapper
public interface TaskInfoMapper {

    List<TaskInfo> map2Model(List<TaskInfoEntity> list);

    TaskInfo map2Model(TaskInfoEntity list);

    TaskInfoEntity map2Entity(TaskInfo obj);

}
