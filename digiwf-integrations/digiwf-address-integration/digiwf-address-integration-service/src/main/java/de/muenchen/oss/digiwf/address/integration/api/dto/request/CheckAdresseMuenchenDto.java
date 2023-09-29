package de.muenchen.oss.digiwf.address.integration.api.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CheckAdresseMuenchenDto extends AbstractRequestDto {

    private String adresse;

    private String strassenname;

    private Integer strasseId;

    private String hausnummer;

    private String zusatz;

    private String plz;

    private String ortsname;

    private String gemeindeschluessel;

}
