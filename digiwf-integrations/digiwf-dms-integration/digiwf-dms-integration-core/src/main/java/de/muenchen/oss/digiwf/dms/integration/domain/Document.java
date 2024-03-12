package de.muenchen.oss.digiwf.dms.integration.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class Document {

    private String procedureCOO;
    private String title;
    private LocalDate date;
    private DocumentType type;
    private List<Content> contents;

}
