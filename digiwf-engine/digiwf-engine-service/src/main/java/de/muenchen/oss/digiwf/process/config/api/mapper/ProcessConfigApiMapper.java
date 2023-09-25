/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.process.config.api.mapper;

import de.muenchen.oss.digiwf.process.config.api.transport.ProcessConfigTO;
import de.muenchen.oss.digiwf.process.config.domain.model.ProcessConfig;
import org.mapstruct.Mapper;

/**
 * Map between {@link ProcessConfigTO} and {@link ProcessConfig}
 *
 * @author externer.dl.horn
 */
@Mapper
public interface ProcessConfigApiMapper {

    ProcessConfig map(ProcessConfigTO obj);

    ProcessConfigTO map2TO(ProcessConfig obj);

}
