package de.muenchen.oss.digiwf.address.integration.api.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SearchAdressenMuenchenDto extends AbstractRequestDto {

    private String query;

    private List<String> plzfilter;

    private List<Long> hausnummerfilter;

    private List<String> buchstabefilter;

    private String searchtype;

    private String sort;

    private String sortdir;

    private Integer page;

    private Integer pagesize;

}
