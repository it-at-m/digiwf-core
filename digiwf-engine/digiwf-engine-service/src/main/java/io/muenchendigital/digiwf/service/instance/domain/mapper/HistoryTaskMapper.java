/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.service.instance.domain.mapper;

import io.muenchendigital.digiwf.service.instance.domain.model.HistoryTask;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Map between {@link HistoryTask} and {@link HistoricTaskInstance}
 *
 * @author externer.dl.horn
 */
@Mapper
public interface HistoryTaskMapper {

    List<HistoryTask> map2Model(List<HistoricTaskInstance> list);

}
