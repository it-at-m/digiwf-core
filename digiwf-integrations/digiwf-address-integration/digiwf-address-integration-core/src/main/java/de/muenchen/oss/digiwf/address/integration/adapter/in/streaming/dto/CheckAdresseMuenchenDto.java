package de.muenchen.oss.digiwf.address.integration.adapter.in.streaming.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckAdresseMuenchenDto {

    private String adresse;

    private String strassenname;

    private Integer strasseId;

    private String hausnummer;

    private String zusatz;

    private String plz;

    private String ortsname;

    private String gemeindeschluessel;

}
