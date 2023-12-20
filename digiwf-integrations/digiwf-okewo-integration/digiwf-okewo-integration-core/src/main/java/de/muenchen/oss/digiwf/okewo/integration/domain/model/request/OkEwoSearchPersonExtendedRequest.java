package de.muenchen.oss.digiwf.okewo.integration.domain.model.request;

import de.muenchen.oss.digiwf.okewo.integration.client.model.SuchePersonerweitertAnfrage;
import lombok.Data;

@Data
public class OkEwoSearchPersonExtendedRequest {

    /**
     * The payload to request OK.EWO.
     */
    private SuchePersonerweitertAnfrage request;

}
