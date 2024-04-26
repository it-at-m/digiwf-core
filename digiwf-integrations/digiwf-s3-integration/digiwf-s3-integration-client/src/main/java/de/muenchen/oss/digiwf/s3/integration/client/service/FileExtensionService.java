package de.muenchen.oss.digiwf.s3.integration.client.service;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class FileExtensionValidation {

    private final Map<String,String> supportedFileExtensions;

    public boolean validate(String fileExtension) {
        return supportedFileExtensions.containsKey(fileExtension);
    }
}
