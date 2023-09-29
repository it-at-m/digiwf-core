package de.muenchen.oss.digiwf.address.integration.model.request;

import lombok.Data;

import java.util.List;

@Data
public class SearchAdressenBundesweitModel {

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
