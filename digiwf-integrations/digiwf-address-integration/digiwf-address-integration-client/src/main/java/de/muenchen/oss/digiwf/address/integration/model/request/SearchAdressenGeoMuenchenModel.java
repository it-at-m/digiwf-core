package de.muenchen.oss.digiwf.address.integration.model.request;

import lombok.Data;

@Data
public class SearchAdressenGeoMuenchenModel {

    private String geometrie;

    private Double lat;

    private Double lng;

    private Double distanz;

    private Double topleftlat;

    private Double topleftlng;

    private Double bottomrightlat;

    private Double bottomrightlng;

    private String format;

}
