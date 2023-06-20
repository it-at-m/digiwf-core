package io.muenchendigital.digiwf.cosys.integration.application.usecase;

import com.google.gson.Gson;
import io.muenchendigital.digiwf.cosys.integration.application.port.in.CreateDocument;
import io.muenchendigital.digiwf.cosys.integration.application.port.out.CorrelateMessagePort;
import io.muenchendigital.digiwf.cosys.integration.application.port.out.SaveFileToStoragePort;
import io.muenchendigital.digiwf.cosys.integration.configuration.CosysConfiguration;
import io.muenchendigital.digiwf.cosys.integration.gen.api.GenerationApi;
import io.muenchendigital.digiwf.cosys.integration.model.GenerateDocument;
import io.muenchendigital.digiwf.message.process.api.error.BpmnError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@RequiredArgsConstructor
public class CreateDocumentUseCase implements CreateDocument {

    public static final String DATA_FILE_NAME = "data";
    public static final String MERGE_FILE_NAME = "merge";

    private final SaveFileToStoragePort saveFileToStoragePort;
    private final CorrelateMessagePort correlateMessagePort;
    private final CosysConfiguration configuration;
    private final GenerationApi generationApi;

    /**
     * Generate a Document in Cosys and save it in S3 using given presigned urls.
     *
     * @param generateDocument Data for generating documents
     */
    @Override
    public void createDocument(final String processInstanceIde, final String messageName, @Valid final GenerateDocument generateDocument) {
        final byte[] data = this.generateCosysDocument(generateDocument).block();
        this.saveFileToStoragePort.saveDocumentInStorage(generateDocument, data);

        final Map<String, Object> correlatePayload = new HashMap<>();
        correlatePayload.put("status", true);
        this.correlateMessagePort.correlateMessage(processInstanceIde,messageName,correlatePayload);
    }

    /**
     * Generate a Document in Cosys
     *
     * @param generateDocument Data for generating documents
     * @return the generated document
     */
    @Override
    public Mono<byte[]> generateCosysDocument(final GenerateDocument generateDocument) {
        try {
            return this.generationApi.generatePdf(
                    generateDocument.getGuid(),
                    generateDocument.getClient(),
                    generateDocument.getRole(),
                    this.createFile(DATA_FILE_NAME, new Gson().toJson(generateDocument.getVariables()).getBytes(StandardCharsets.UTF_8)),
                    null,
                    null,
                    null,
                    null,
                    null,
                    false,
                    this.createFile(MERGE_FILE_NAME, this.configuration.getMergeOptions()),
                    null,
                    null
            );
        } catch (final Exception ex) {
            log.error("Document could not be created.", ex);
            throw new BpmnError("COSYS_DOCUMENT_CREATION_FAILED", ex.getMessage());
        }
    }

    //------------------------------------------ helper methods ------------------------------------------//

    private File createFile(final String name, final byte[] content) throws IOException {
        final Path tempFile = Files.createTempFile(name, ".json");
        Files.write(tempFile, content);
        return tempFile.toFile();
    }

}
