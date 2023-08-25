package de.muenchen.oss.digiwf.address.service.integration.model.request;

import lombok.Data;

import java.util.List;

@Data
public class ListStrassenModel {

    private List<String> stadtbezirksnamen;

    private List<Long> stadtbezirksnummern;

    private String strassenname;

    private String sortdir;

    private Integer page;
    
    private Integer pagesize;

}
