package de.muenchen.oss.digiwf.address.service.integration.api.dto.response;

import de.muenchen.oss.digiwf.address.service.integration.gen.model.AdresseDistanz;
import lombok.Data;

import java.util.List;

@Data
public class AddressDistancesDto {

    List<AdresseDistanz> adresseDistances;

}
