package de.muenchen.oss.digiwf.dms.integration.adapter.out.s3;

import de.muenchen.oss.digiwf.dms.integration.application.port.out.LoadFileOutPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.TransferContentOutPort;
import de.muenchen.oss.digiwf.dms.integration.domain.Content;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.PropertyNotSetException;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFileRepository;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFolderRepository;
import de.muenchen.oss.digiwf.s3.integration.client.service.FileExtensionService;
import de.muenchen.oss.digiwf.s3.integration.client.service.S3StorageUrlProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class S3Adapter implements LoadFileOutPort, TransferContentOutPort {

    private static final String LOAD_FOLDER_FAILED = "LOAD_FOLDER_FAILED";

    private final DocumentStorageFileRepository documentStorageFileRepository;
    private final DocumentStorageFolderRepository documentStorageFolderRepository;
    private final FileExtensionService fileExtensionService;
    private final S3StorageUrlProvider s3DomainService;

    @Override
    public List<Content> loadFiles(final List<String> filepaths, final String fileContext, final String processDefinition) {
        final String s3Storage;
        try {
            s3Storage = s3DomainService.provideS3StorageUrl(processDefinition);
        } catch (final PropertyNotSetException e) {
            throw new BpmnError(LOAD_FOLDER_FAILED, e.getMessage());
        }
        final List<Content> contents = new ArrayList<>();
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

    private List<Content> getFilesFromFolder(String folderpath, final String domainSpecificS3Storage) {
        try {
            final List<Content> contents = new ArrayList<>();
            final Set<String> filepath;
            filepath = documentStorageFolderRepository.getAllFilesInFolderRecursively(folderpath, domainSpecificS3Storage).block();
            if (Objects.isNull(filepath)) throw new BpmnError(LOAD_FOLDER_FAILED, "An folder could not be loaded from url: " + folderpath);
            filepath.forEach(file -> contents.add(getFile(file, domainSpecificS3Storage)));
            return contents;
        } catch (final DocumentStorageException | DocumentStorageServerErrorException | DocumentStorageClientErrorException e) {
            throw new BpmnError(LOAD_FOLDER_FAILED, "An folder could not be loaded from url: " + folderpath);
        }
    }

    private Content getFile(String filepath, final String domainSpecificS3Storage) {
        try {
            final byte[] bytes;
            bytes = this.documentStorageFileRepository.getFile(filepath, 3, domainSpecificS3Storage);
            final String mimeType = fileExtensionService.detectFileType(bytes);
            final String filename = FilenameUtils.getBaseName(filepath);

            // check if mimeType exists
            if (!fileExtensionService.isSupported(mimeType))
                throw new BpmnError("FILE_TYPE_NOT_SUPPORTED", "The type of this file is not supported: " + filepath);

            return new Content(fileExtensionService.getFileExtension(mimeType), filename, bytes);
        } catch (final DocumentStorageException | DocumentStorageServerErrorException | DocumentStorageClientErrorException e) {
            throw new BpmnError("LOAD_FILE_FAILED", "An file could not be loaded from url: " + filepath);
        }
    }

    @Override
    public void transferContent(final List<Content> content, final String filepath, final String fileContext, final String processDefinitionId) {
        final String s3Storage;
        try {
            s3Storage = s3DomainService.provideS3StorageUrl(processDefinitionId);
        } catch (final PropertyNotSetException e) {
            throw new BpmnError("SAVE_FILE_FAILED", e.getMessage());
        }

        val fullPath = fileContext + "/" + filepath;

        for (val file : content) {
            try {
                this.documentStorageFileRepository.saveFile(fullPath + "/" + file.getName() + "." + file.getExtension(), file.getContent(), 1, null,
                        s3Storage);
            } catch (Exception e) {
                throw new BpmnError("SAVE_FILE_FAILED", "An file could not be saved to path: " + fullPath);
            }
        }
    }
}
