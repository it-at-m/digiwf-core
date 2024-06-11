package de.muenchen.oss.digiwf.s3.integration.adapter.in.streaming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilesDTO {

    private String filePaths;

    private String fileContext;

    public List<String> getFilePathsAsList() {
        return Arrays.asList(filePaths.split("[,;]"));
    }
}
