package de.muenchen.oss.digiwf.dms.integration.adapter.out.s3;

import de.muenchen.oss.digiwf.dms.integration.application.port.out.LoadFilePort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.TransferContentPort;
import de.muenchen.oss.digiwf.dms.integration.domain.Content;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.PropertyNotSetException;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFileRepository;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFolderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class S3Adapter implements LoadFilePort, TransferContentPort {

    private final DocumentStorageFileRepository documentStorageFileRepository;

    private final DocumentStorageFolderRepository documentStorageFolderRepository;

    private final Map<String, String> supportedExtensions;

    @Override
    public List<Content> loadFiles(final List<String> filepaths, final String fileContext) {

        List<Content> contents = new ArrayList<>();

        filepaths.forEach(path -> {
            String fullPath = fileContext + "/" + path;
            if (fullPath.endsWith("/")) {
                contents.addAll(getFilesFromFolder(fullPath));
            } else {
                contents.add(getFile(fullPath));
            }
        });

        return contents;

    }

    private List<Content> getFilesFromFolder(String folderpath) {
        try {
            List<Content> contents = new ArrayList<>();
            Set<String> filepath = documentStorageFolderRepository.getAllFilesInFolderRecursively(folderpath).block();
            filepath.forEach(file -> {
                contents.add(getFile(file));
            });
            return contents;
        } catch (final DocumentStorageException | DocumentStorageServerErrorException |
                       DocumentStorageClientErrorException | PropertyNotSetException e) {
            throw new BpmnError("LOAD_FOLDER_FAILED", "An folder could not be loaded from url: " + folderpath);
        }
    }

    private Content getFile(String filepath) {
        try {
            final Tika tika = new Tika();
            final byte[] bytes = this.documentStorageFileRepository.getFile(filepath, 3);
            final String type = tika.detect(bytes);
            final String filename = FilenameUtils.getBaseName(filepath);


            final String extension = supportedExtensions.entrySet()
                    .stream()
                    .filter(set -> set.getValue().equals(type))
                    .findFirst()
                    .map(Map.Entry::getKey)
                    .orElseThrow(() -> new BpmnError("FILE_TYPE_NOT_SUPPORTED", "The type of this file is not supported: " + filepath));

            return new Content(extension, filename, bytes);

        } catch (final DocumentStorageException | DocumentStorageServerErrorException |
                       DocumentStorageClientErrorException | PropertyNotSetException e) {
            throw new BpmnError("LOAD_FILE_FAILED", "An file could not be loaded from url: " + filepath);
        }
    }

    @Override
    public void transferContent(List<Content> content, String filepath, String fileContext) {
        val fullPath = fileContext + "/" + filepath;

        for (val file : content) {
            try {
                this.documentStorageFileRepository.saveFile(fullPath + "/" + file.getName() + "." + file.getExtension(), file.getContent(), 1, null);
            } catch (Exception e) {
                throw new BpmnError("SAVE_FILE_FAILED", "An file could not be saved to path: " + fullPath);
            }
        }
    }
}
