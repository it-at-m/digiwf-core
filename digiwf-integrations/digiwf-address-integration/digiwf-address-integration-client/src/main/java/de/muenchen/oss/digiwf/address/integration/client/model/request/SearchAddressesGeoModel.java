package de.muenchen.oss.digiwf.address.integration.client.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchAddressesGeoModel {

    private String geometry;

    private Double lat;

    private Double lng;

    private Double distance;

    private Double topleftlat;

    private Double topleftlng;

    private Double bottomrightlat;

    private Double bottomrightlng;

    private String format;

}
