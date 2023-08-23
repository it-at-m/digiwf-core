package de.muenchen.oss.digiwf.okewo.integration.api.dto.request;

import de.muenchen.oss.digiwf.okewo.integration.gen.model.SuchePersonAnfrage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SearchPersonRequestDto extends AbstractRequestDto {

    private SuchePersonAnfrage searchPerson;

}
