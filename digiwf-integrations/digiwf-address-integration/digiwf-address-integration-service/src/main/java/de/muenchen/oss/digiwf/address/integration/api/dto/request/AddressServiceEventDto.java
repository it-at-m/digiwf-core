package de.muenchen.oss.digiwf.address.integration.api.dto.request;

import lombok.Data;

@Data
public class AddressServiceEventDto {

    /**
     * The payload to request address service.
     */
    private AbstractRequestDto request;

}
