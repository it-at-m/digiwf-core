package de.muenchen.oss.digiwf.address.integration.client.model.response;

import de.muenchen.oss.digiwf.address.integration.client.gen.model.AdresseDistanz;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class AddressDistancesModel {

    List<AdresseDistanz> addressDistances;

}
