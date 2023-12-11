package de.muenchen.oss.digiwf.email.model;

import jakarta.mail.util.ByteArrayDataSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class FileAttachment {

    private String fileName;

    private ByteArrayDataSource file;

}
