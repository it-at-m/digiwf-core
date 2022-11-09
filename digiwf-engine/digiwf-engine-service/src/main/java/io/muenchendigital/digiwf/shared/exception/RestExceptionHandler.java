/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.shared.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Exceptionhandler for global domain exceptions.
 * Transforms them to HTTP Status Codes.
 *
 * @
 */
@Slf4j
@ControllerAdvice
public class RestExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ObjectNotFoundException.class, IllegalArgumentException.class})
    protected ResponseEntity<Object> handleNotFound(final RuntimeException ex, final WebRequest request) {
        final String bodyOfResponse = "Object not found";
        log.error("Client Exception 404.", ex);
        return this.handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {VariablesNotValidException.class, NoFileContextException.class})
    protected ResponseEntity<Object> handleBadRequest(final RuntimeException ex, final WebRequest request) {
        final String bodyOfResponse = "Bad request";
        log.error("Client Exception 400.", ex);
        return this.handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {IllegalResourceAccessException.class})
    protected ResponseEntity<Object> handleForbidden(final RuntimeException ex, final WebRequest request) {
        final String bodyOfResponse = "Forbidden";
        log.error("Client Exception 403.", ex);
        return this.handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value = {ConflictingResourceException.class})
    protected ResponseEntity<Object> handleConflict(final RuntimeException ex, final WebRequest request) {
        final String bodyOfResponse = "Conflict";
        log.error("Client Exception 409.", ex);
        return this.handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
