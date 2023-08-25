package io.muenchendigital.digiwf.okverkehr.integration.api.dto.request;

import lombok.Data;

@Data
public class OkVerkehrEventDto {

    /**
     * The payload to request OK.Verkehr.
     */
    private AbstractRequestDto request;

}
