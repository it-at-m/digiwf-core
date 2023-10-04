package de.muenchen.oss.digiwf.address.integration.adapter.in.streaming.dto;

import lombok.Data;

import java.util.List;

@Data
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
