package de.muenchen.oss.digiwf.address.integration.model.request;

import lombok.Data;

@Data
public class ListAenderungenMuenchenModel {

    private String wirkungsdatumvon;

    private String wirkungsdatumbis;

    private String strassenname;

    private Long hausnummer;

    private String plz;

    private String zusatz;

    private String sort;

    private String sortdir;

    private Integer page;

    private Integer pagesize;

}
