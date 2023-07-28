/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.legacy.dms.muc.external.transport;

import lombok.Builder;

/**
 * Data model for the result of searching for dms objects.
 *
 * @author martin.dietrich
 */
@Builder
public class DMSSearchResult {

    public String objaddress;

    public String objname;

}
