package de.muenchen.oss.digiwf.address.integration.api.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ListAenderungenMuenchenDto extends AbstractRequestDto {

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
