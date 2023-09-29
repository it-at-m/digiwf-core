package de.muenchen.oss.digiwf.address.integration.model.response;

import de.muenchen.oss.digiwf.address.integration.gen.model.AdresseDistanz;
import lombok.Data;

import java.util.List;

@Data
public class AddressDistancesModel {

    List<AdresseDistanz> adresseDistances;

}
