/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.dms.alwdms.process.readschriftstuecke;

import de.muenchen.oss.digiwf.legacy.dms.alwdms.domain.model.AlwMetadata;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Metadata object with the corresponding field key.
 *
 * @author externer.dl.horn
 */
@Data
@Builder
public class KvrReadSchriftstueckeData implements Serializable {

    /**
     * Key of the form field.
     */
    private String fieldKey;

    /**
     * Meatadata of the choosen documents.
     */
    private List<AlwMetadata> schriftstuecke;
}
