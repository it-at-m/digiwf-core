package io.muenchendigital.digiwf.okewo.integration.api.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OrdnungsmerkmalDto extends AbstractRequestDto {

    private String ordnungsmerkmal;

}
