package de.muenchen.oss.digiwf.address.integration.model.request;

import lombok.Data;

@Data
public class CheckAdresseMuenchenModel {

    private String adresse;

    private String strassenname;

    private Integer strasseId;

    private String hausnummer;

    private String zusatz;

    private String plz;

    private String ortsname;

    private String gemeindeschluessel;

}
