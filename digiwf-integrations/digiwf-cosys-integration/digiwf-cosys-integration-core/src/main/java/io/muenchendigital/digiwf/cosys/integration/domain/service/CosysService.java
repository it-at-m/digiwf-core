package io.muenchendigital.digiwf.cosys.integration.domain.service;

import com.google.gson.Gson;
import io.muenchendigital.digiwf.cosys.integration.configuration.CosysConfiguration;
import io.muenchendigital.digiwf.cosys.integration.domain.model.DocumentStorageUrl;
import io.muenchendigital.digiwf.cosys.integration.domain.model.GenerateDocument;
import io.muenchendigital.digiwf.cosys.integration.gen.api.GenerationApi;
import io.muenchendigital.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import io.muenchendigital.digiwf.s3.integration.client.exception.DocumentStorageException;
import io.muenchendigital.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import io.muenchendigital.digiwf.s3.integration.client.repository.transfer.S3FileTransferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Service
@RequiredArgsConstructor
public class CosysService {

    public static final String DATA_FILE_NAME = "data";
    public static final String MERGE_FILE_NAME = "merge";

    private final S3FileTransferRepository s3FileTransferRepository;
    private final CosysConfiguration configuration;
    private final GenerationApi generationApi;

    /**
     * Generate a Document in Cosys and save it in S3 using given presigned urls.
     *
     * @param generateDocument Data for generating documents
     */
    public void createDocument(@Valid final GenerateDocument generateDocument) {
        final byte[] data = this.generateCosysDocument(generateDocument).block();
        this.saveDocumentInS3(generateDocument, data);
    }

    /**
     * Generate a Document in Cosys
     *
     * @param generateDocument Data for generating documents
     * @return
     */
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
            throw new RuntimeException("Document could not be created.");
        }
    }

    //------------------------------------------ helper methods ------------------------------------------//

    private File createFile(final String name, final byte[] content) throws IOException {
        final Path tempFile = Files.createTempFile(name, ".json");
        Files.write(tempFile, content);
        return tempFile.toFile();
    }

    private void saveDocumentInS3(final GenerateDocument generateDocument, final byte[] data) {
        try {
            for (final DocumentStorageUrl presignedUrl : generateDocument.getDocumentStorageUrls()) {
                if (presignedUrl.getAction().equalsIgnoreCase("POST")) {
                    this.s3FileTransferRepository.saveFile(presignedUrl.getUrl(), data);
                } else if (presignedUrl.getAction().equalsIgnoreCase("PUT")) {
                    this.s3FileTransferRepository.updateFile(presignedUrl.getUrl(), data);
                } else {
                    throw new RuntimeException("Document could not be saved.");
                }
            }
        } catch (final DocumentStorageClientErrorException | DocumentStorageServerErrorException | DocumentStorageException ex) {
            log.error("Document could not be saved.", ex);
            throw new RuntimeException("Document could not be saved.");
        }
    }

}
