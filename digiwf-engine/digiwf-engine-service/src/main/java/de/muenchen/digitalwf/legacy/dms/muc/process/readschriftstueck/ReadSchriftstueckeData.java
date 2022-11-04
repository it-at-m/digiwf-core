/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.digitalwf.legacy.dms.muc.process.readschriftstueck;

import de.muenchen.digitalwf.legacy.dms.muc.domain.model.Metadata;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class ReadSchriftstueckeData implements Serializable {

    private String fieldKey;
    private List<Metadata> schriftstuecke;
}
