/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.humantask.api.mapper;

import de.muenchen.oss.digiwf.humantask.api.transport.HumanTaskDetailTO;
import de.muenchen.oss.digiwf.humantask.api.transport.HumanTaskTO;
import de.muenchen.oss.digiwf.humantask.domain.model.HumanTask;
import de.muenchen.oss.digiwf.humantask.domain.model.HumanTaskDetail;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Map {@link HumanTask} domain object into {@link HumanTaskTO} transport object.
 *
 * @author externer.dl.horn
 */
@Mapper
public interface HumanTaskApiMapper {

    List<HumanTaskTO> map2TO(List<HumanTask> list);
    HumanTaskTO map2TO(HumanTask task);

    HumanTaskDetailTO map2TO(HumanTaskDetail taskDetail);

}
