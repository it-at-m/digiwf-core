package de.muenchen.oss.digiwf.okewo.integration.dto.request;

import lombok.Data;

@Data
public class OkEwoEventDto {

    /**
     * The payload to request OK.EWO.
     */
    private AbstractRequestDto request;

}
