package de.muenchen.oss.digiwf.cosys.integration.adapter.in.streaming;

import com.fasterxml.jackson.databind.JsonNode;
import de.muenchen.oss.digiwf.cosys.integration.domain.model.GenerateDocument;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class GenerateDocumentDTO extends GenerateDocument {
    @NotBlank
    private String fileContext;

    @NotBlank
    private String filePath;

    public GenerateDocumentDTO(
            @NotBlank(message = "client is mandatory") String client,
            @NotBlank(message = "role is mandatory") String role,
            @NotBlank(message = "guid is mandatory") String guid,
            JsonNode variables,
            String fileContext,
            String filePath) {
        super(client, role, guid, variables);
        this.fileContext = fileContext;
        this.filePath = filePath;
    }
}
