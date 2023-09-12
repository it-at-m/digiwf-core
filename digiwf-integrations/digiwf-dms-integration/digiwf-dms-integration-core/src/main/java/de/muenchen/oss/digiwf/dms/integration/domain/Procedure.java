package de.muenchen.oss.digiwf.dms.integration.domain;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Procedure {

    private String coo;
    private String fileCOO;
    private String title;

    public Procedure(final String fileCOO, final String title) {
        this.fileCOO = fileCOO;
        this.title = title;
    }

}
