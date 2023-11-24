package de.muenchen.oss.digiwf.okewo.integration.domain.model.request;

import lombok.Data;

@Data
public class OkEwoEventRequest<T> {

    /**
     * The payload to request OK.EWO.
     */
    private T request;

}
