package io.muenchendigital.digiwf.s3.integration.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileData {

    private String pathToFile;

    private Integer expiresInMinutes;

    private LocalDate endOfLife;

}
