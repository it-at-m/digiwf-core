package de.muenchen.oss.digiwf.s3.integration.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileSize {

    public static final int LENGTH_PATH_TO_FILE = 1024;

    private long fileSize;
}
