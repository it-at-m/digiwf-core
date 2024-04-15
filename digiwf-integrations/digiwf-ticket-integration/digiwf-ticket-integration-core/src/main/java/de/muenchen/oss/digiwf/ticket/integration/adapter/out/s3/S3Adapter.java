package de.muenchen.oss.digiwf.ticket.integration.adapter.out.s3;

import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.process.api.config.api.ProcessConfigApi;
import de.muenchen.oss.digiwf.process.api.config.api.dto.ConfigEntryTO;
import de.muenchen.oss.digiwf.process.api.config.api.dto.ProcessConfigTO;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.PropertyNotSetException;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFileRepository;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFolderRepository;
import de.muenchen.oss.digiwf.ticket.integration.application.port.out.LoadFileOutPort;
import de.muenchen.oss.digiwf.ticket.integration.domain.model.FileContent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class S3Adapter implements LoadFileOutPort {

    private final DocumentStorageFileRepository documentStorageFileRepository;
    private final DocumentStorageFolderRepository documentStorageFolderRepository;
    private final ProcessConfigApi processConfigApi;
    private final List<String> supportedExtensions;

    private final String APP_FILE_S3_SYNC_CONFIG = "app_file_s3_sync_config";

    @Override
    public List<FileContent> loadFiles(final List<String> filepaths, final String fileContext, final String processDefinition) {
        final String s3Storage = getDomainSpecificS3Storage(processDefinition).orElse(null);
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

    private List<FileContent> getFilesFromFolder(String folderpath, final String domainSpecificS3Storage) {
        try {
            final List<FileContent> contents = new ArrayList<>();
            final Set<String> filepath;
            if (domainSpecificS3Storage != null) {
                filepath = documentStorageFolderRepository.getAllFilesInFolderRecursively(folderpath, domainSpecificS3Storage).block();
            } else {
                filepath = documentStorageFolderRepository.getAllFilesInFolderRecursively(folderpath).block();
            }
            filepath.forEach(file -> contents.add(getFile(file, domainSpecificS3Storage)));
            return contents;
        } catch (final DocumentStorageException | DocumentStorageServerErrorException |
                       DocumentStorageClientErrorException | PropertyNotSetException e) {
            throw new BpmnError("LOAD_FOLDER_FAILED", "An folder could not be loaded from url: " + folderpath);
        }
    }

    private FileContent getFile(String filepath, final String domainSpecificS3Storage) {
        try {
            final Tika tika = new Tika();
            final byte[] bytes;
            if (domainSpecificS3Storage != null) {
                bytes = this.documentStorageFileRepository.getFile(filepath, 3, domainSpecificS3Storage);
            } else {
                bytes = this.documentStorageFileRepository.getFile(filepath, 3);
            }
            final String mimeType = tika.detect(bytes);
            final String filename = FilenameUtils.getName(filepath);

            // check if mimeType exists
            supportedExtensions
                    .stream()
                    .filter(extension -> extension.equals(mimeType))
                    .findAny()
                    .orElseThrow(() -> new BpmnError("FILE_TYPE_NOT_SUPPORTED", "The type of this file is not supported: " + filepath));

            return new FileContent(mimeType, filename, bytes);
        } catch (final DocumentStorageException | DocumentStorageServerErrorException |
                       DocumentStorageClientErrorException | PropertyNotSetException e) {
            throw new BpmnError("LOAD_FILE_FAILED", "An file could not be loaded from url: " + filepath);
        }
    }

    private Optional<String> getDomainSpecificS3Storage(final String processDefinition) {
        try {
            final ProcessConfigTO processConfig = processConfigApi.getProcessConfig(processDefinition);
            return processConfig.getConfigs().stream()
                    .filter(cfg -> cfg.getKey().equals(APP_FILE_S3_SYNC_CONFIG))
                    .findAny()
                    .map(ConfigEntryTO::getValue);
        } catch (final Exception e) {
            return Optional.empty();
        }

    }

}
