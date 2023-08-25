package de.muenchen.oss.digiwf.address.service.integration.api.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SearchAdressenGeoMuenchenDto extends AbstractRequestDto {

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
