package de.muenchen.oss.digiwf.dms.integration.adapter.out.s3;

import de.muenchen.oss.digiwf.dms.integration.application.port.out.LoadFilePort;
import de.muenchen.oss.digiwf.dms.integration.domain.File;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.PropertyNotSetException;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;


import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class S3Adapter implements LoadFilePort {

    private final DocumentStorageFileRepository documentStorageFileRepository;

    @Override
    public List<File> loadFiles(final List<String> filepaths){

        List<File> files = new ArrayList<>();

        try {
            final Tika tika = new Tika();
            final byte[] bytes = this.documentStorageFileRepository.getFile(filepaths.get(0), 3);
            final String type = tika.detect(bytes);
            File file = new File(type, "",bytes);
            files.add(file);
        } catch (final DocumentStorageException | DocumentStorageServerErrorException | DocumentStorageClientErrorException | PropertyNotSetException e) {
            log.error("An file could not be loaded from url: {}", filepaths);
            throw new BpmnError("LOAD_FILE_FAILED", "An file could not be loaded from url: " + filepaths);
        }

        return files;

    }


}
