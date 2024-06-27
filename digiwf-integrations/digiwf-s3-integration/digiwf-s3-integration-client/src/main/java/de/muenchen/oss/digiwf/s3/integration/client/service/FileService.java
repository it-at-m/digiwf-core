package de.muenchen.oss.digiwf.s3.integration.client.service;

import de.muenchen.oss.digiwf.s3.integration.client.exception.NoFileTypeException;
import lombok.Getter;
import org.apache.tika.Tika;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.util.unit.DataSize;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A service class for managing file extensions, detecting file types and checking file size.
 */
public class FileService {

    private static final String NO_FILE_EXTENSION = "No file extension found for %s";

    /**
     * Map stores supported file extensions and their corresponding MIME types. If it is empty, all types are supported.
     */
    private final Map<String, String> supportedFileExtensions;

    @Getter
    private final DataSize maxFileSize;

    @Getter
    private final DataSize maxFolderSize;

    public FileService(final Map<String, String> supportedFileExtensions, final DataSize maxFileSize, final DataSize maxFolderSize) {
        this.supportedFileExtensions = Objects.nonNull(supportedFileExtensions) ? supportedFileExtensions : new HashMap<>();
        this.maxFileSize = maxFileSize;
        this.maxFolderSize = maxFolderSize;
    }

    public boolean isValidFileSize(final byte[] file) {
        return isValidBatchSize(file.length);
    }

    public boolean isValidFileSize(final long fileSizeInBytes) {
        return DataSize.ofBytes(fileSizeInBytes).compareTo(maxFileSize) <= 0;
    }

    public DataSize getTotalBatchSize(final Map<String, Long> fileSizesWithPaths){
        return DataSize.ofBytes(fileSizesWithPaths.values().stream().mapToLong(Long::valueOf).sum());
    }

    public boolean isValidBatchSize(final long folderSizeInBytes) {
        return DataSize.ofBytes(folderSizeInBytes).compareTo(maxFolderSize) <= 0;
    }

    public boolean isValidBatchSize(final DataSize folderSizeInBytes) {
        return folderSizeInBytes.compareTo(maxFolderSize) <= 0;
    }

    public Map<String, Long> getOversizedFiles(final Map<String, Long> fileSizesWithPaths) {
        return fileSizesWithPaths.entrySet().stream()
                .filter(entry -> !isValidFileSize(entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
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
        MimeType mimeType;
        try {
            mimeType = allMimeTypes.forName(type);
        } catch (MimeTypeException e) {
            throw new NoFileTypeException(NO_FILE_EXTENSION + type);
        }
        final String extension = mimeType.getExtension();
        final int lastDotIndex = extension.lastIndexOf('.');
        if (lastDotIndex == -1) throw new NoFileTypeException(NO_FILE_EXTENSION + type);
        final String fileExtension = extension.substring(lastDotIndex + 1);
        if (fileExtension.isEmpty()) throw new NoFileTypeException(NO_FILE_EXTENSION + type);
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
