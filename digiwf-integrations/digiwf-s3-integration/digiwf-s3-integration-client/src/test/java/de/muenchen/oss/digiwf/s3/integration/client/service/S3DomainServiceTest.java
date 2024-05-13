package de.muenchen.oss.digiwf.s3.integration.client.service;

import de.muenchen.oss.digiwf.s3.integration.client.exception.PropertyNotSetException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class S3DomainServiceTest {

    @Test
    void getDefaultDocumentStorageUrl() throws PropertyNotSetException {
        var domainService = new S3DomainService(null,null);
        Assertions.assertThrows(PropertyNotSetException.class, domainService::getDefaultDocumentStorageUrl);

        domainService = new S3DomainService(null, "url");
        assertThat(domainService.getDefaultDocumentStorageUrl(), is("url"));
    }

}
