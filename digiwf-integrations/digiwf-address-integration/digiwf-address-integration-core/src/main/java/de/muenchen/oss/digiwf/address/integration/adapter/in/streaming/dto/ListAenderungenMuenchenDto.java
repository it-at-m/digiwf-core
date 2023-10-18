package de.muenchen.oss.digiwf.address.integration.adapter.in.streaming.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListAenderungenMuenchenDto {

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
