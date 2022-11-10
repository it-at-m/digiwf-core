/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.dms.alwdms.process.readschriftstuecke;

import io.muenchendigital.digiwf.legacy.dms.shared.BaseSchriftstueck;
import io.muenchendigital.digiwf.legacy.dms.shared.BaseSchriftstueckeData;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Loaded document data with the corresponding field key.
 *
 * @author externer.dl.horn
 */
@Data
@Builder
public class KvrReadSchriftstueckeResult implements BaseSchriftstueckeData, Serializable {

    /**
     * Key of the corresponding
     */
    private String fieldKey;

    /**
     * Data of the loaded documents
     */
    private BaseSchriftstueck[] schriftstuecke;
}
