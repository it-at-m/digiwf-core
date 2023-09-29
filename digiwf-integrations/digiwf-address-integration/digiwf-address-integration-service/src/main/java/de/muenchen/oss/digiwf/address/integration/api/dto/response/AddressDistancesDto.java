package de.muenchen.oss.digiwf.address.integration.api.dto.response;

import de.muenchen.oss.digiwf.address.integration.gen.model.AdresseDistanz;
import lombok.Data;

import java.util.List;

@Data
public class AddressDistancesDto {

    List<AdresseDistanz> adresseDistances;

}
