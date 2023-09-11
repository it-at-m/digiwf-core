package de.muenchen.oss.digiwf.dms.integration.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Document {

    private String coo;
    private String vorgangCoo;
    private String title;
    private DocumentType type;
    private List<File> files = new ArrayList<>();

    public Document(final String vorgangCoo, final String title, final DocumentType art, final List<File> schriftstuecke) {
        this.vorgangCoo = vorgangCoo;
        this.title = title;
        this.type = type;
        this.files.addAll(schriftstuecke);
    }

}
