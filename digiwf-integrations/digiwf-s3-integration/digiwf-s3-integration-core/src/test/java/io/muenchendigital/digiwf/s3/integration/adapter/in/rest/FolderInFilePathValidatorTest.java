package io.muenchendigital.digiwf.s3.integration.adapter.in.rest;

import io.muenchendigital.digiwf.s3.integration.adapter.in.rest.validation.FolderInFilePathValidator;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class FolderInFilePathValidatorTest {

    private final FolderInFilePathValidator folderInFilePathValidator = new FolderInFilePathValidator();

    @Test
    void isValid() {
        assertThat(this.folderInFilePathValidator.isValid("folder/file.txt", null), is(true));
        assertThat(this.folderInFilePathValidator.isValid("folder/subfolder/file.txt", null), is(true));
        assertThat(this.folderInFilePathValidator.isValid("file.txt", null), is(false));
        assertThat(this.folderInFilePathValidator.isValid("", null), is(false));
        assertThat(this.folderInFilePathValidator.isValid(null, null), is(false));
    }
}