package de.muenchen.oss.digiwf.cosys.integration.adapter.out;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {


    public static File createFile(final String name, final byte[] content) throws IOException {
        final Path tempFile = Files.createTempFile(name, ".json");
        Files.write(tempFile, content);
        return tempFile.toFile();
    }
}
