package de.muenchen.oss.digiwf.address.integration.adapter.in.streaming.dto;

import lombok.Data;

@Data
public class SearchAdressenGeoMuenchenDto {

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
