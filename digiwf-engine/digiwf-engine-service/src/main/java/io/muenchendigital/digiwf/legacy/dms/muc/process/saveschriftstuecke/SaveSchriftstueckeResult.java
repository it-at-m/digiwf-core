/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.dms.muc.process.saveschriftstuecke;

import io.muenchendigital.digiwf.legacy.dms.muc.domain.model.Metadata;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class SaveSchriftstueckeResult implements Serializable {

    private String fieldKey;
    private List<Metadata> schriftstuecke;
}
