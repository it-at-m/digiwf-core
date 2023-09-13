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
    private List<File> files = new ArrayList<>();

    public Document(final String procedureCOO, final String title, final DocumentType type, final List<File> files) {
        this.procedureCOO = procedureCOO;
        this.title = title;
        this.type = type;
        this.files.addAll(files);
    }

}
