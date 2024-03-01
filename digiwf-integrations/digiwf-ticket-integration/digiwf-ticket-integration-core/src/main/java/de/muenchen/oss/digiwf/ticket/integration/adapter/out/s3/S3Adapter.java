package de.muenchen.oss.digiwf.ticket.integration.adapter.out.s3;

import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.PropertyNotSetException;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFileRepository;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFolderRepository;
import de.muenchen.oss.digiwf.ticket.integration.application.port.out.LoadFilePort;
import de.muenchen.oss.digiwf.ticket.integration.domain.model.FileContent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class S3Adapter implements LoadFilePort {

    private final DocumentStorageFileRepository documentStorageFileRepository;
    private final DocumentStorageFolderRepository documentStorageFolderRepository;
    private final List<String> supportedExtensions;

    @Override
    public List<FileContent> loadFiles(final List<String> filepaths) {
        final List<FileContent> contents = new ArrayList<>();
        filepaths.forEach(path -> {
            if (path.endsWith("/")) {
                contents.addAll(getFilesFromFolder(path));
            } else {
                contents.add(getFile(path));
            }
        });
        return contents;
    }

    private List<FileContent> getFilesFromFolder(String folderpath) {
        try {
            final List<FileContent> contents = new ArrayList<>();
            final Set<String> filepath = documentStorageFolderRepository.getAllFilesInFolderRecursively(folderpath).block();
            filepath.forEach(file -> contents.add(getFile(file)));
            return contents;
        } catch (final DocumentStorageException | DocumentStorageServerErrorException |
                       DocumentStorageClientErrorException | PropertyNotSetException e) {
            throw new BpmnError("LOAD_FOLDER_FAILED", "An folder could not be loaded from url: " + folderpath);
        }
    }

    private FileContent getFile(String filepath) {
        try {
            final Tika tika = new Tika();
            final byte[] bytes = this.documentStorageFileRepository.getFile(filepath, 3);
            final String mimeType = tika.detect(bytes);
            final String filename = FilenameUtils.getBaseName(filepath);

            // check if mimeType exists
            supportedExtensions
                    .stream()
                    .filter(extension -> extension.equals(mimeType))
                    .findAny()
                    .orElseThrow(() -> new BpmnError("FILE_TYPE_NOT_SUPPORTED", "The type of this file is not supported: " + filepath));

            return new FileContent(mimeType, filename, new String(bytes, StandardCharsets.UTF_8));
        } catch (final DocumentStorageException | DocumentStorageServerErrorException |
                       DocumentStorageClientErrorException | PropertyNotSetException e) {
            throw new BpmnError("LOAD_FILE_FAILED", "An file could not be loaded from url: " + filepath);
        }
    }

}
