package de.muenchen.oss.digiwf.address.integration.adapter.in.streaming.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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
