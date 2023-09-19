package de.muenchen.oss.digiwf.dms.integration.adapter.out.s3;

import de.muenchen.oss.digiwf.dms.integration.application.port.out.LoadFilePort;
import de.muenchen.oss.digiwf.dms.integration.domain.Content;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static de.muenchen.oss.digiwf.process.api.config.ProcessConfigConstants.DIGIWF_S3_SYNC_CONFIG;

@Slf4j
@RequiredArgsConstructor
public class S3Adapter implements LoadFilePort {

    private final DocumentStorageFileRepository documentStorageFileRepository;

    private final DocumentStorageFolderRepository documentStorageFolderRepository;

    private final List<String> supportedExtensions;

    private final ProcessConfigApi processConfigApi;

    @Override
    public List<Content> loadFiles(final List<String> filepaths, final String fileContext, final String processInstance){

        List<Content> contents = new ArrayList<>();

        filepaths.forEach(path -> {
            String fullPath = fileContext + "/" + path;
            if (fullPath.endsWith("/")) {
                contents.addAll(getFilesFromFolder(fullPath, processInstance));
            } else {
                contents.add(getFile(fullPath,processInstance));
            }
        });

        return contents;

    }

    private List<Content> getFilesFromFolder(String folderpath, String processInstance) {
        try {
            Optional<String> customS3 = this.getCustomS3IntegrationUrl(processInstance);
            List<Content> contents = new ArrayList<>();
            Set<String> filepath = customS3.isPresent()
                    ? documentStorageFolderRepository.getAllFilesInFolderRecursively(folderpath,customS3.get()).block()
                    : documentStorageFolderRepository.getAllFilesInFolderRecursively(folderpath).block();
            filepath.forEach(file -> {
                contents.add(getFile(file,processInstance));
            });
            return contents;
        } catch (final DocumentStorageException | DocumentStorageServerErrorException | DocumentStorageClientErrorException | PropertyNotSetException e) {
            log.error("An folder could not be loaded from url: {}", folderpath);
            throw new BpmnError("LOAD_FOLDER_FAILED", "An folder could not be loaded from url: " + folderpath);
        }
    }

    private Content getFile (String filepath, String processInstance) {
        try {
            Optional<String> customS3 = this.getCustomS3IntegrationUrl(processInstance);
            final Tika tika = new Tika();
            final byte[] bytes = customS3.isPresent()
                    ? this.documentStorageFileRepository.getFile(filepath,3,customS3.get())
                    : this.documentStorageFileRepository.getFile(filepath, 3);
            final String type = tika.detect(bytes);
            final String filename = FilenameUtils.getBaseName(filepath);

            if(!supportedExtensions.contains(type.toLowerCase())) {
                log.error("The type of this file is not supported: {}", filepath);
                throw new BpmnError("FILE_TYPE_NOT_SUPPORTED", "The type of this file is not supported: " + filepath);
            }

            return new Content(type, filename, bytes);

        } catch (final DocumentStorageException | DocumentStorageServerErrorException | DocumentStorageClientErrorException | PropertyNotSetException e) {
            log.error("An file could not be loaded from url: {}", filepath);
            throw new BpmnError("LOAD_FILE_FAILED", "An file could not be loaded from url: " + filepath);
        }
    }

    private Optional<String> getCustomS3IntegrationUrl (String processInstance) {

        ProcessConfigTO processConfig = processConfigApi.getProcessConfig(processInstance);

        return  processConfig.getConfigs().stream()
                .filter(config -> config.getKey().equalsIgnoreCase(DIGIWF_S3_SYNC_CONFIG))
                .map(ConfigEntryTO::getValue)
                .findAny();
    }


}
