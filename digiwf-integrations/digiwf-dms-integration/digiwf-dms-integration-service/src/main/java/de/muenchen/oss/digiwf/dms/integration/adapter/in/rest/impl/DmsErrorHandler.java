package de.muenchen.oss.digiwf.dms.integration.adapter.in.rest.impl;

import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class DmsErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { BpmnError.class })
    protected ResponseEntity<ErrorDto> handleConflict(BpmnError bpmnError, WebRequest request) {
        log.warn("A technical error occured with error message {}", bpmnError.getErrorMessage());
        return new ResponseEntity<>(new ErrorDto(bpmnError.getErrorCode(), bpmnError.getErrorMessage()), HttpStatus.BAD_REQUEST);
    }
}