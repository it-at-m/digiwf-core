package de.muenchen.oss.digiwf.email.integration.adapter.out.s3;

import de.muenchen.oss.digiwf.email.integration.application.port.out.LoadMailAttachmentOutPort;
import de.muenchen.oss.digiwf.email.integration.domain.model.presigned.PresignedUrl;
import de.muenchen.oss.digiwf.email.model.FileAttachment;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFileRepository;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFolderRepository;
import de.muenchen.oss.digiwf.s3.integration.client.repository.transfer.S3FileTransferRepository;
import de.muenchen.oss.digiwf.s3.integration.client.service.FileService;
import de.muenchen.oss.digiwf.s3.integration.client.service.S3StorageUrlProvider;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class S3Adapter implements LoadMailAttachmentOutPort {

    public static final String LOAD_FOLDER_FAILED = "LOAD_FOLDER_FAILED";
    public static final String LOAD_FILE_FAILED = "LOAD_FILE_FAILED";
    private final S3FileTransferRepository s3FileTransferRepository;
    private final DocumentStorageFileRepository documentStorageFileRepository;
    private final DocumentStorageFolderRepository documentStorageFolderRepository;
    private final FileService fileService;
    private final S3StorageUrlProvider s3DomainService;

    @Deprecated
    @Override
    public FileAttachment loadAttachment(final PresignedUrl attachment) throws BpmnError {
        try {
            final String fileName = StringUtils.substringAfterLast(attachment.getPath(), "/");
            final byte[] bytes = this.s3FileTransferRepository.getFile(attachment.getUrl());
            final String type = fileService.detectFileType(bytes);
            // Note: Create the ByteArrayDataSource with the bytes and the type to avoid auto type detection by ByteArrayDataSource
            // https://github.com/it-at-m/digiwf-core/issues/616
            final ByteArrayDataSource file = new ByteArrayDataSource(bytes, type);
            file.setName(fileName);
            return new FileAttachment(fileName, file);
        } catch (final DocumentStorageException | DocumentStorageServerErrorException |
                       DocumentStorageClientErrorException e) {
            log.debug("An attachment could not be loaded from presigned url: {}", attachment);
            throw new BpmnError(LOAD_FILE_FAILED, "An attachment could not be loaded from presigned url: " + attachment);
        }
    }

    @Override
    public List<FileAttachment> loadAttachments(final String fileContext, final List<String> filePaths) {
        final List<FileAttachment> attachments = new ArrayList<>();
        filePaths.forEach(path -> {
            final String fullPath = fileContext + "/" + path;
            if (fullPath.endsWith("/")) {
                attachments.addAll(getFilesFromFolder(fullPath));
            } else {
                attachments.add(getFile(fullPath));
            }
        });
        return attachments;
    }

    private List<FileAttachment> getFilesFromFolder(final String folderPath) {
        try {
            final List<FileAttachment> contents = new ArrayList<>();
            final Set<String> filepath;
            filepath = documentStorageFolderRepository.getAllFilesInFolderRecursively(folderPath, s3DomainService.getDefaultDocumentStorageUrl()).block();
            if (Objects.isNull(filepath))
                throw new BpmnError(LOAD_FOLDER_FAILED, "An folder could not be loaded from url: " + folderPath);
            filepath.forEach(file -> contents.add(getFile(file)));
            return contents;
        } catch (final DocumentStorageException | DocumentStorageServerErrorException |
                       DocumentStorageClientErrorException e) {
            throw new BpmnError(LOAD_FOLDER_FAILED, "An folder could not be loaded from path: " + folderPath);
        }
    }

    private FileAttachment getFile(final String filePath) {
        try {
            final byte[] bytes;
            bytes = this.documentStorageFileRepository.getFile(filePath, 3, s3DomainService.getDefaultDocumentStorageUrl());
            final String mimeType = fileService.detectFileType(bytes);
            final String filename = FilenameUtils.getName(filePath);
            final ByteArrayDataSource file = new ByteArrayDataSource(bytes, mimeType);

            // check if mimeType exists
            if (!fileService.isSupported(mimeType))
                throw new BpmnError("FILE_TYPE_NOT_SUPPORTED", "The type of this file is not supported: " + filePath);

            return new FileAttachment(filename, file);
        } catch (final DocumentStorageException | DocumentStorageServerErrorException |
                       DocumentStorageClientErrorException e) {
            throw new BpmnError(LOAD_FILE_FAILED, "An file could not be loaded from path: " + filePath);
        }
    }
}
