package de.muenchen.oss.digiwf.address.integration.adapter.in.streaming.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchAdressenMuenchenDto {

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
