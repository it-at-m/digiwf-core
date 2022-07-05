package io.muenchendigital.digiwf.s3.integration.domain.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FileData {

    private String pathToFile;

    private Integer expiresInMinutes;

    private LocalDate endOfLife;

}
