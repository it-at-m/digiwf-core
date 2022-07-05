package io.muenchendigital.digiwf.s3.integration.domain.model;

import lombok.Data;

import java.util.Set;

@Data
public class FilesInFolder {

    private Set<String> pathToFiles;

}
