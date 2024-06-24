package de.muenchen.oss.digiwf.ticket.integration.adapter.out.s3;

import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.PropertyNotSetException;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFileRepository;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFolderRepository;
import de.muenchen.oss.digiwf.s3.integration.client.service.FileExtensionService;
import de.muenchen.oss.digiwf.s3.integration.client.service.S3StorageUrlProvider;
import de.muenchen.oss.digiwf.ticket.integration.application.port.out.LoadFileOutPort;
import de.muenchen.oss.digiwf.ticket.integration.domain.model.FileContent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class S3Adapter implements LoadFileOutPort {

    private static final String LOAD_FOLDER_FAILED = "LOAD_FOLDER_FAILED";

    private final DocumentStorageFileRepository documentStorageFileRepository;
    private final DocumentStorageFolderRepository documentStorageFolderRepository;
    private final FileExtensionService fileExtensionService;
    private final S3StorageUrlProvider s3StorageUrlProvider;

    @Override
    public List<FileContent> loadFiles(final List<String> filepaths, final String fileContext, final String processDefinition) {
        final String s3Storage;
        try {
            s3Storage = s3StorageUrlProvider.provideS3StorageUrl(processDefinition);
        } catch (PropertyNotSetException e) {
            throw new BpmnError(LOAD_FOLDER_FAILED, e.getMessage());
        }
        final List<FileContent> contents = new ArrayList<>();
        filepaths.forEach(path -> {
            final String fullPath = fileContext + "/" + path;
            if (fullPath.endsWith("/")) {
                contents.addAll(getFilesFromFolder(fullPath, s3Storage));
            } else {
                contents.add(getFile(fullPath, s3Storage));
            }
        });
        return contents;
    }

    private List<FileContent> getFilesFromFolder(String folderPath, final String domainSpecificS3Storage) {
        try {
            final List<FileContent> contents = new ArrayList<>();
            final Set<String> filepath;
            filepath = documentStorageFolderRepository.getAllFilesInFolderRecursively(folderPath, domainSpecificS3Storage).block();
            if (Objects.isNull(filepath)) throw new BpmnError(LOAD_FOLDER_FAILED, "An folder could not be loaded from url: " + folderPath);
            filepath.forEach(file -> contents.add(getFile(file, domainSpecificS3Storage)));
            return contents;
        } catch (final NullPointerException | DocumentStorageException | DocumentStorageServerErrorException | DocumentStorageClientErrorException e) {
            throw new BpmnError(LOAD_FOLDER_FAILED, "An folder could not be loaded from url: " + folderPath);
        }
    }

    private FileContent getFile(String filepath, final String domainSpecificS3Storage) {
        try {
            final byte[] bytes;
            bytes = this.documentStorageFileRepository.getFile(filepath, 3, domainSpecificS3Storage);
            final String mimeType = fileExtensionService.detectFileType(bytes);
            final String filename = FilenameUtils.getName(filepath);

            // check if mimeType exists
            if (!fileExtensionService.isSupported(mimeType))
                throw new BpmnError("FILE_TYPE_NOT_SUPPORTED", "The type of this file is not supported: " + filepath);

            return new FileContent(mimeType, filename, bytes);
        } catch (final DocumentStorageException | DocumentStorageServerErrorException | DocumentStorageClientErrorException e) {
            throw new BpmnError("LOAD_FILE_FAILED", "An file could not be loaded from url: " + filepath);
        }
    }

}
