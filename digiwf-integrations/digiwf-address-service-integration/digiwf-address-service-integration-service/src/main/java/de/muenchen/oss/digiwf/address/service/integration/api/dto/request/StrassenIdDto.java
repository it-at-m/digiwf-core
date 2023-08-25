package de.muenchen.oss.digiwf.address.service.integration.api.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class StrassenIdDto extends AbstractRequestDto {

    private Long strasseId;

}
