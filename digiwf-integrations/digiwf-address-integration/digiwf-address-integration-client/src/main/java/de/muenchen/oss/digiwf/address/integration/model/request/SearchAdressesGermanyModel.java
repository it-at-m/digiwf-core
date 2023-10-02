package de.muenchen.oss.digiwf.address.integration.model.request;

import lombok.Data;

import java.util.List;

@Data
public class SearchAdressesGermanyModel {

    private String query;

    private String zip;

    private String cityName;

    private String gemeindeschluessel;

    private List<Long> houseNumberFilter;

    private List<String> letterFilter;

    private String sort;

    private String sortdir;

    private Integer page;

    private Integer pagesize;

}
