package io.muenchendigital.digiwf.s3.integration.adapter.out.s3;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class S3AndDatabaseAsyncException extends Exception {

    public S3AndDatabaseAsyncException(final String message) {
        super(message);
    }

}
