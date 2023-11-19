package de.muenchen.oss.digiwf.s3.integration.adapter.in.rest.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import de.muenchen.oss.digiwf.s3.integration.adapter.in.rest.validation.FolderInFilePath;
import de.muenchen.oss.digiwf.s3.integration.domain.model.FileData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Schema(description = "File data for requesting a presigned url")
public class FileDataDto {

    @NotEmpty
    @Size(max = FileData.LENGTH_PATH_TO_FILE)
    @FolderInFilePath
    private String pathToFile;

    /**
     * Definition of the validity period of the presigned URL.
     */
    @NotNull
    @Min(FileData.MIN_EXPIRES_IN_MINUTES)
    private Integer expiresInMinutes;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate endOfLife;

}
