package de.muenchen.oss.digiwf.s3.integration.client.service;

import de.muenchen.oss.digiwf.s3.integration.client.exception.NoFileTypeException;
import org.apache.tika.Tika;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A service class for managing file extensions and detecting file types.
 */
public class FileExtensionService {

    /**
     * Map stores supported file extensions and their corresponding MIME types. If it is empty, all types are supported.
     */
    private final Map<String, String> supportedFileExtensions;

    public FileExtensionService(final Map<String, String> supportedFileExtensions) {
        this.supportedFileExtensions = Objects.nonNull(supportedFileExtensions) ? supportedFileExtensions : new HashMap<>();
    }

    /**
     * Checks if a given file type is supported.
     *
     * @param type the file type to check.
     * @return {@code true} if the file type is supported, {@code false} otherwise.
     */
    public boolean isSupported(final String type) {
        return supportedFileExtensions.isEmpty() || supportedFileExtensions.containsKey(type) || supportedFileExtensions.containsValue(type);
    }

    /**
     * Retrieves the file extension for a given MIME type.
     *
     * @param type the MIME type for which to retrieve the file extension.
     * @return the file extension.
     * @throws NoFileTypeException if no file extension is found for the given MIME type.
     */
    public String getFileExtension(final String type) {
        final MimeTypes allMimeTypes = MimeTypes.getDefaultMimeTypes();
        MimeType mimeType = null;
        try {
            mimeType = allMimeTypes.forName(type);
        } catch (MimeTypeException e) {
            throw new NoFileTypeException("No file extension found for " + type);
        }
        final String extension = mimeType.getExtension();
        final int lastDotIndex = extension.lastIndexOf('.');
        if (lastDotIndex == -1) throw new NoFileTypeException("No file extension found for " + type);
        final String fileExtension = extension.substring(lastDotIndex + 1);
        if (fileExtension.isEmpty()) throw new NoFileTypeException("No file extension found for " + type);
        return fileExtension;
    }

    /**
     * Detects the file type from a byte array.
     *
     * @param fileContent the byte array representing the file content.
     * @return the detected file type as a MIME type string.
     */
    public String detectFileType(final byte[] fileContent) {
        final Tika tika = new Tika();
        return tika.detect(fileContent);
    }
}
