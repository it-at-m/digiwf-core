/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.dms.muc.process.readschriftstueck;

import io.muenchendigital.digiwf.legacy.dms.shared.BaseSchriftstueck;
import io.muenchendigital.digiwf.legacy.dms.shared.BaseSchriftstueckeData;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ReadSchriftstueckeResult implements BaseSchriftstueckeData, Serializable {

    private String fieldKey;

    private BaseSchriftstueck[] schriftstuecke;
}
