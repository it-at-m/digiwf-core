package io.muenchendigital.digiwf.s3.integration.adapter.in.rest;

import io.muenchendigital.digiwf.s3.integration.application.port.in.FileExistenceException;
import io.muenchendigital.digiwf.s3.integration.application.port.in.FileSystemAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionMapping {

  @ExceptionHandler
  public ResponseEntity<String> handleFileExistenceException(FileExistenceException exception) {
    return ResponseEntity.badRequest().body(exception.getMessage());
  }

}
