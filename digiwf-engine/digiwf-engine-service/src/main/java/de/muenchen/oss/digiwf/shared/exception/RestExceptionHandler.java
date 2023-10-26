/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2023
 */

package de.muenchen.oss.digiwf.shared.exception;

import de.muenchen.oss.digiwf.json.validation.DigiWFValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Exception handler for global domain exceptions.
 * Transforms them to HTTP Status Codes.
 */
@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {ObjectNotFoundException.class, IllegalArgumentException.class})
    public ResponseEntity<Object> handleNotFound(final RuntimeException ex, final WebRequest request) {
        final String bodyOfResponse = "Object not found";
        log.error("Client Exception 404.", ex);
        return this.handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {VariablesNotValidException.class, NoFileContextException.class, DigiWFValidationException.class})
    public ResponseEntity<Object> handleBadRequest(final RuntimeException ex, final WebRequest request) {
        final String bodyOfResponse = "Bad request";
        log.error("Client Exception 400.", ex);
        return this.handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {IllegalResourceAccessException.class})
    public ResponseEntity<Object> handleForbidden(final RuntimeException ex, final WebRequest request) {
        final String bodyOfResponse = "Forbidden";
        log.error("Client Exception 403.", ex);
        return this.handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value = {ConflictingResourceException.class})
    public ResponseEntity<Object> handleConflict(final RuntimeException ex, final WebRequest request) {
        final String bodyOfResponse = "Conflict";
        log.error("Client Exception 409.", ex);
        return this.handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(body, headers, status);
    }
}
