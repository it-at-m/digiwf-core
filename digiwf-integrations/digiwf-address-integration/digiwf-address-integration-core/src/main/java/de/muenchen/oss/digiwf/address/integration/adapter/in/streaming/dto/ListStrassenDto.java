package de.muenchen.oss.digiwf.address.integration.adapter.in.streaming.dto;

import lombok.Data;

import java.util.List;

@Data
public class ListStrassenDto {

    private List<String> stadtbezirksnamen;

    private List<Long> stadtbezirksnummern;

    private String strassenname;

    private String sortdir;

    private Integer page;

    private Integer pagesize;

}
