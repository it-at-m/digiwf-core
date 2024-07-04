package de.muenchen.oss.digiwf.s3.integration.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class FileSizesInFolder {

    private Map<String, Long> fileSizes;
}
