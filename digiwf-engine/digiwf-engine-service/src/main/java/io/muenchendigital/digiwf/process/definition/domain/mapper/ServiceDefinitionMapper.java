/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.process.definition.domain.mapper;

import io.muenchendigital.digiwf.process.definition.domain.model.ServiceDefinition;
import io.muenchendigital.digiwf.process.definition.domain.model.ServiceDefinitionDetail;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Map between {@link ServiceDefinition} and {@link ProcessDefinition}
 *
 * @author externer.dl.horn
 */
@Mapper
public interface ServiceDefinitionMapper {

    List<ServiceDefinition> map(List<ProcessDefinition> list);

    ServiceDefinitionDetail map(ProcessDefinition processDefinition);

}
