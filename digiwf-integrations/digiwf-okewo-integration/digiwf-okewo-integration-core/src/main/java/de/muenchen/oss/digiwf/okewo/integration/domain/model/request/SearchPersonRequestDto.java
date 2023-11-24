package de.muenchen.oss.digiwf.okewo.integration.domain.model.request;

import de.muenchen.oss.digiwf.okewo.integration.client.model.SuchePersonAnfrage;
import lombok.Data;

@Data
public class SearchPersonRequestDto {

    private SuchePersonAnfrage searchPerson;

}
