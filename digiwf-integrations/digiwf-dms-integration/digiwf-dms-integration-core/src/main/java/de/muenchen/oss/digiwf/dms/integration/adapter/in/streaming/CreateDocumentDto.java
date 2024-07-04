package de.muenchen.oss.digiwf.dms.integration.adapter.in.streaming;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CreateDocumentDto {

    private String procedureCoo;

    private String title;
    private LocalDate date;

    private String user;

    private String type;

    private String filepaths;

    private String fileContext;

    public List<String> getFilepathsAsList() {
        return Arrays.asList(filepaths.split(","));
    }

}
