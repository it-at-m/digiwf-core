/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.digitalwf.legacy.dms.muc.process.readschriftstueck;

import de.muenchen.digitalwf.legacy.dms.shared.BaseSchriftstueck;
import de.muenchen.digitalwf.legacy.dms.shared.BaseSchriftstueckeData;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ReadSchriftstueckeResult implements BaseSchriftstueckeData, Serializable {

    private String fieldKey;

    private BaseSchriftstueck[] schriftstuecke;
}
