package de.muenchen.oss.digiwf.email.integration.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import jakarta.mail.util.ByteArrayDataSource;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class FileAttachment {

    private String fileName;

    private ByteArrayDataSource file;

}
