package io.muenchendigital.digiwf.legacy.dms.shared;

import org.apache.tika.Tika;

import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class S3Resolver {

    public static byte[] getS3File(final String s3Url) throws IOException {
        final byte[] documentContent;
        final URL binaryFile = new URL(s3Url);
        final Tika tika = new Tika();
        final InputStream fileInputStream = binaryFile.openStream();
        final ByteArrayDataSource file = new ByteArrayDataSource(fileInputStream, tika.detect(binaryFile));
        documentContent = file.getInputStream().readAllBytes();
        return documentContent;
    }
}
