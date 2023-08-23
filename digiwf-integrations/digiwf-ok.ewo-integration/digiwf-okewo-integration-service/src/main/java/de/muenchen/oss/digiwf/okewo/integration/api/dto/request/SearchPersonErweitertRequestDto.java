package de.muenchen.oss.digiwf.okewo.integration.api.dto.request;

import de.muenchen.oss.digiwf.okewo.integration.gen.model.SuchePersonerweitertAnfrage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SearchPersonErweitertRequestDto extends AbstractRequestDto {

    private SuchePersonerweitertAnfrage searchPersonErweitert;

}
