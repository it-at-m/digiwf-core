package io.muenchendigital.digiwf.email.integration.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.mail.util.ByteArrayDataSource;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class FileAttachment {

    private String fileName;

    private ByteArrayDataSource file;

}
