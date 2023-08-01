package io.muenchendigital.digiwf.s3.integration.example.client.controller;

import io.muenchendigital.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import io.muenchendigital.digiwf.s3.integration.client.exception.DocumentStorageException;
import io.muenchendigital.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import io.muenchendigital.digiwf.s3.integration.client.exception.PropertyNotSetException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AllEndpointsUsageController {

  private static final String LOG_DIVIDER = "------------";

  private final ClientFileUsageController clientFileUsageController;

  private final ClientFolderUsageController clientFolderUsageController;

  @GetMapping("/all")
  @ResponseStatus(HttpStatus.OK)
  public void getFile() throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, IOException, PropertyNotSetException {

    System.err.println("FILE usage");
    this.clientFileUsageController.saveFile();
    System.err.println(LOG_DIVIDER);
    this.clientFileUsageController.updateFile();
    System.err.println(LOG_DIVIDER);
    this.clientFileUsageController.getFile();
    System.err.println(LOG_DIVIDER);
    this.clientFileUsageController.updateEndOfLife();
    System.err.println(LOG_DIVIDER);
    this.clientFileUsageController.deleteFile();

    System.err.println("FILE INPUTSTREAM usage");
    this.clientFileUsageController.saveFileInputStream();
    System.err.println(LOG_DIVIDER);
    this.clientFileUsageController.updateFileInputStream();
    System.err.println(LOG_DIVIDER);
    this.clientFileUsageController.getFileInputStream();
    System.err.println(LOG_DIVIDER);
    this.clientFileUsageController.updateEndOfLife();
    System.err.println(LOG_DIVIDER);
    this.clientFileUsageController.deleteFile();

    System.err.println("FOLDER usage");
    this.clientFileUsageController.saveFile();
    System.err.println(LOG_DIVIDER);
    this.clientFolderUsageController.getAllFilesInFolderRecursively();
    System.err.println(LOG_DIVIDER);
    this.clientFolderUsageController.deleteFolder();
  }
}
