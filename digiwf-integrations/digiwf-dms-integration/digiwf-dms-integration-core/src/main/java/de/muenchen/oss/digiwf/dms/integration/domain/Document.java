package de.muenchen.oss.digiwf.dms.integration.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Document {

    private String coo;
    private String procedureCOO;
    private String title;
    private DocumentType type;
    private List<Content> contents = new ArrayList<>();

    public Document(final String procedureCOO, final String title, final DocumentType type, final List<Content> contents) {
        this.procedureCOO = procedureCOO;
        this.title = title;
        this.type = type;
        this.contents.addAll(contents);
    }

}
