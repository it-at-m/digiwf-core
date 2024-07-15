package de.muenchen.oss.digiwf.cosys.integration.adapter.in.streaming;

import com.fasterxml.jackson.databind.JsonNode;
import de.muenchen.oss.digiwf.cosys.integration.domain.model.DocumentStorageUrl;
import de.muenchen.oss.digiwf.cosys.integration.domain.model.GenerateDocument;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Deprecated
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class GenerateDocumentPresignedUrlsDTO extends GenerateDocument {
    /**
     * A list of presigned urls that are used to save the cosys documents in a s3 storage
     */
    @Valid
    @Size(min = 1, max = 1)
    private List<DocumentStorageUrl> documentStorageUrls;

    public GenerateDocumentPresignedUrlsDTO(
            @NotBlank(message = "client is mandatory") String client,
            @NotBlank(message = "role is mandatory") String role,
            @NotBlank(message = "guid is mandatory") String guid,
            JsonNode variables,
            List<DocumentStorageUrl> documentStorageUrls) {
        super(client, role, guid, variables);
        this.documentStorageUrls = documentStorageUrls;
    }
}
