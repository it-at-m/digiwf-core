package io.muenchendigital.digiwf.message.example.process.api;

import io.muenchendigital.digiwf.message.example.process.error.TechnicalError;
import io.muenchendigital.digiwf.message.process.api.ProcessApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandling {

    private final ProcessApi processApi;

    @ExceptionHandler(TechnicalError.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleRuntimeException(final TechnicalError ex) {
        log.warn("Handle technical error");
        this.processApi.handleBpmnError(ex.getProcessInstanceId(), ex.getErrorCode(), ex.getErrorMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleRuntimeException(final RuntimeException ex) {
        log.error("An exception occurred: ", ex);
        log.warn("Handle incident");
        this.processApi.handleIncident("dummy-process-instance", "dummy-origin-message", ex.getMessage());
    }
}
