package de.muenchen.oss.digiwf.address.service.integration.api.dto.request;

import lombok.Data;

@Data
public class AddressServiceEventDto {

    /**
     * The payload to request address service.
     */
    private AbstractRequestDto request;

}
