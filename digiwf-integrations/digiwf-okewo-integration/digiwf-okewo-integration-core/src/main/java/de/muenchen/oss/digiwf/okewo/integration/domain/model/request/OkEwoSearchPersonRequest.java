package de.muenchen.oss.digiwf.okewo.integration.domain.model.request;

import de.muenchen.oss.digiwf.okewo.integration.client.model.SuchePersonAnfrage;
import lombok.Data;

@Data
public class OkEwoSearchPersonRequest {

    /**
     * The payload to request OK.EWO.
     */
    private SuchePersonAnfrage request;

}
