package de.muenchen.oss.digiwf.s3.integration.client.service;

import de.muenchen.oss.digiwf.s3.integration.client.exception.NoFileTypeException;
import org.apache.tika.Tika;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FileExtensionService {

    private final Map<String, String> supportedFileExtensions;

    public FileExtensionService(final Map<String, String> supportedFileExtensions) {
        this.supportedFileExtensions = Objects.nonNull(supportedFileExtensions) ? supportedFileExtensions : new HashMap<>();
    }

    public boolean isSupported(final String type) {
        if (supportedFileExtensions.isEmpty()) return true;
        if (supportedFileExtensions.containsKey(type)) return true;
        return supportedFileExtensions.containsValue(type);
    }

    public String getFileExtension(final String type) {
        final MimeTypes allMimeTypes = MimeTypes.getDefaultMimeTypes();
        MimeType mimeType = null;
        try {
            mimeType = allMimeTypes.forName(type);
        } catch (MimeTypeException e) {
            throw new NoFileTypeException("No file extension found for " + type);
        }
        return mimeType.getExtension();
    }

    public String detectFileType(final byte[] fileContent) {
        final Tika tika = new Tika();
        return tika.detect(fileContent);
    }
}
