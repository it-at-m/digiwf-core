package io.muenchendigital.digiwf.okverkehr.integration.api.dto.request;

import io.muenchendigital.digiwf.okverkehr.integration.gen.model.HalterPersonAnfrage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GetHalterRequestDto extends AbstractRequestDto {

    private HalterPersonAnfrage halterPersonAnfrage;

}
