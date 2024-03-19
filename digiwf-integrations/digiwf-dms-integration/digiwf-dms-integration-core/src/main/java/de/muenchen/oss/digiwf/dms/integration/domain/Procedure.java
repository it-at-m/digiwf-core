package de.muenchen.oss.digiwf.dms.integration.domain;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Procedure {

    private String coo;
    private String fileCOO;
    private String title;
    private String fileSubj;

    public Procedure(final String fileCOO, final String title, final String fileSubj) {
        this.fileCOO = fileCOO;
        this.title = title;
        this.fileSubj = fileSubj;
    }

}
