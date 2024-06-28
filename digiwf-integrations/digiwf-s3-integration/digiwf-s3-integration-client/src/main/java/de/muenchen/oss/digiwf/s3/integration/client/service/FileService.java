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

    /** The maximum allowed file size. A value of 0 indicates no limits. */
    @Getter
    private final DataSize maxFileSize;

    /** The maximum allowed batch size. A value of 0 indicates no limits. */
    @Getter
    private final DataSize maxBatchSize;

    public FileService(final Map<String, String> supportedFileExtensions, final DataSize maxFileSize, final DataSize maxBatchSize) {
        this.supportedFileExtensions = Objects.nonNull(supportedFileExtensions) ? supportedFileExtensions : new HashMap<>();
        this.maxFileSize = maxFileSize;
        this.maxBatchSize = maxBatchSize;
    }

    /**
     * Checks if the given file size is valid.
     *
     * @param file the byte array representing the file content.
     * @return {@code true} if the file size is valid, {@code false} otherwise.
     */
    public boolean isValidFileSize(final byte[] file) {
        return isValidFileSize(file.length);
    }

    /**
     * Checks if the given file size is valid.
     *
     * @param fileSizeInBytes the size of the file in bytes.
     * @return {@code true} if the file size is valid, {@code false} otherwise.
     */
    public boolean isValidFileSize(final long fileSizeInBytes) {
        if (Objects.isNull(maxFileSize) || maxFileSize.toBytes() == 0L) return true;
        return DataSize.ofBytes(fileSizeInBytes).compareTo(maxFileSize) <= 0;
    }

    /**
     * Calculates the total size of a batch of files.
     *
     * @param fileSizesWithPaths a map of file paths and their corresponding sizes.
     * @return the total size of the batch as a DataSize object.
     */
    public DataSize getTotalBatchSize(final Map<String, Long> fileSizesWithPaths) {
        return DataSize.ofBytes(fileSizesWithPaths.values().stream().mapToLong(Long::valueOf).sum());
    }

    /**
     * Checks if the given batch size is valid.
     *
     * @param batchSizeInBytes the size of the batch in bytes.
     * @return {@code true} if the batch size is valid, {@code false} otherwise.
     */
    public boolean isValidBatchSize(final long batchSizeInBytes) {
        return DataSize.ofBytes(batchSizeInBytes).compareTo(maxBatchSize) <= 0;
    }

    /**
     * Checks if the given batch size is valid.
     *
     * @param batchSizeInBytes the size of the batch as a  {@link DataSize} object.
     * @return {@code true} if the batch size is valid, {@code false} otherwise.
     */
    public boolean isValidBatchSize(final DataSize batchSizeInBytes) {
        if (Objects.isNull(maxBatchSize) || maxBatchSize.toBytes() == 0L) return true;
        return batchSizeInBytes.compareTo(maxBatchSize) <= 0;
    }

    /**
     * Retrieves a map of oversized files.
     *
     * @param fileSizesWithPaths a map containing file paths and their corresponding sizes.
     * @return a map of oversized files.
     */
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
            throw new NoFileTypeException(String.format(NO_FILE_EXTENSION, type));
        }
        final String extension = mimeType.getExtension();
        final int lastDotIndex = extension.lastIndexOf('.');
        if (lastDotIndex == -1) throw new NoFileTypeException(String.format(NO_FILE_EXTENSION, type));
        final String fileExtension = extension.substring(lastDotIndex + 1);
        if (fileExtension.isEmpty()) throw new NoFileTypeException(String.format(NO_FILE_EXTENSION, type));
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
