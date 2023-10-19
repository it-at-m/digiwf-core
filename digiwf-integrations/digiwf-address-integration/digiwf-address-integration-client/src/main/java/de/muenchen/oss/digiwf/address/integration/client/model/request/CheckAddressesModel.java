package de.muenchen.oss.digiwf.address.integration.client.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckAddressesModel {

    private String address;

    private String streetName;

    private Integer streetId;

    private String houseNumber;

    private String additionalInfo;

    private String zip;

    private String cityName;

    private String gemeindeschluessel;

}
