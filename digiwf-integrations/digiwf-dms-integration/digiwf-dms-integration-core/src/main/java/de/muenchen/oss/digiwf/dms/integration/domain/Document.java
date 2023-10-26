package de.muenchen.oss.digiwf.dms.integration.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Document {

    private String procedureCOO;
    private String title;
    private DocumentType type;
    private List<Content> contents;

}
