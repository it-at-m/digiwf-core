package de.muenchen.oss.digiwf.address.service.integration.api.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SearchAdressenBundesweitDto extends AbstractRequestDto {

    private String query;

    private String plz;

    private String ortsname;

    private String gemeindeschluessel;

    private List<Long> hausnummerfilter;

    private List<String> buchstabefilter;

    private String sort;

    private String sortdir;

    private Integer page;

    private Integer pagesize;

}
