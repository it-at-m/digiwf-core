package io.muenchendigital.digiwf.task.service.application.port.out.file;

import io.muenchendigital.digiwf.task.service.domain.PresignedUrlAction;
import org.springframework.lang.NonNull;
import org.springframework.web.client.HttpServerErrorException;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * Handler template for fetching presigned urls from s3 storage with for specific action.
 */
public interface PresignedUrlPort {

    /**
     * Obtain a presigned url from the default s3 integration service. The default s3 service is specified as property digiwf.s3.url.
     *
     * @param pathToFile
     * @param expireInMinutes specifies how long the presigned url will be valid
     * @return
     * @throws HttpServerErrorException
     */
    String getPresignedUrl(final String pathToFile, final int expireInMinutes, final PresignedUrlAction action) throws HttpServerErrorException;

    /**
     * Obtain a presigned url from the specified s3 integration service
     *
     * @param documentStorageUrl is the url for the s3 integration service
     * @param pathToFile
     * @param expireInMinutes    specifies how long the presigned url will be valid
     * @return
     * @throws HttpServerErrorException
     */
    String getPresignedUrl(final String documentStorageUrl, final String pathToFile, final int expireInMinutes, final PresignedUrlAction action) throws HttpServerErrorException;

}
