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
public class ListStrassenDto {

    private List<String> stadtbezirksnamen;

    private List<Long> stadtbezirksnummern;

    private String strassenname;

    private String sortdir;

    private Integer page;

    private Integer pagesize;

}
