package io.muenchendigital.digiwf.s3.integration.client.service;

import io.muenchendigital.digiwf.s3.integration.client.exception.PropertyNotSetException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class ApiClientFactoryTest {

    @Test
    void getDefaultDocumentStorageUrl() throws PropertyNotSetException {
        var apiClientFactory = new ApiClientFactory(null, null);
        Assertions.assertThrows(PropertyNotSetException.class, apiClientFactory::getDefaultDocumentStorageUrl);

        apiClientFactory = new ApiClientFactory("url", null);
        assertThat(apiClientFactory.getDefaultDocumentStorageUrl(), is("url"));
    }

}
