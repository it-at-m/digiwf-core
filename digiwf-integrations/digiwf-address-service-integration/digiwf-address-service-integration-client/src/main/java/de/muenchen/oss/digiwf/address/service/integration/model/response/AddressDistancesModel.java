package de.muenchen.oss.digiwf.address.service.integration.model.response;

import de.muenchen.oss.digiwf.address.service.integration.gen.model.AdresseDistanz;
import lombok.Data;

import java.util.List;

@Data
public class AddressDistancesModel {

    List<AdresseDistanz> adresseDistances;

}
