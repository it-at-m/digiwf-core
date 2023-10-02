package de.muenchen.oss.digiwf.address.integration.model.request;

import lombok.Data;

@Data
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
