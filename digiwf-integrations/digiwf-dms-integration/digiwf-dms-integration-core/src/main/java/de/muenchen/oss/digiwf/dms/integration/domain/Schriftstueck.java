package de.muenchen.oss.digiwf.dms.integration.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Schriftstueck {

    private String extension;
    private String name;
    private byte[] content;
    private String coo;

    public Schriftstueck(final String extension, final String name, final byte[] content) {
        this.extension = extension;
        this.name = name;
        this.content = content;
    }
}
