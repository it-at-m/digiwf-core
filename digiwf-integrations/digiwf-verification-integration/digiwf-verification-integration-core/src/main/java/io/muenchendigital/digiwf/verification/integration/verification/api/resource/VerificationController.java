package io.muenchendigital.digiwf.verification.integration.verification.api.resource;

import io.muenchendigital.digiwf.verification.integration.verification.domain.exception.CorrelationException;
import io.muenchendigital.digiwf.verification.integration.verification.domain.exception.VerificationExpiredException;
import io.muenchendigital.digiwf.verification.integration.verification.domain.exception.VerificationTokenNotFoundException;
import io.muenchendigital.digiwf.verification.integration.verification.domain.service.VerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Web endpoint to receive the activated token.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class VerificationController {

    public static final String ENDPOINT_VERIFICATION = "/verify";

    public static final String STATE_ERROR      = "Es ist ein Fehler aufgetreten";
    public static final String STATE_SUCCESS    = "Geschafft";

    private final VerificationService verificationService;

    @GetMapping(value = ENDPOINT_VERIFICATION)
    public String verify(@RequestParam final String token, final Model model) {
        log.info("Incoming verification for token: {}", token);
        String message, status;
        try {
            verificationService.verify(token);
            message = "Vielen Dank für Ihre Bestätigung";
            status = STATE_SUCCESS;
        } catch (final VerificationTokenNotFoundException e) {
            log.warn("Verification token not found: {}", token);
            message = "Der Bestätigungstoken wurde nicht gefunden";
            status = STATE_ERROR;
        } catch (final VerificationExpiredException e) {
            log.warn("Verification expired for token: {}", token);
            message = "Die Bestätigungsfrist ist bereits abgelaufen";
            status = STATE_ERROR;
        } catch (final CorrelationException e) {
            log.error("Correlation failed for token: {}", token);
            message = "Die Bestätigung konnte leider nicht zugestellt werden";
            status = STATE_ERROR;
        }
        log.info("Verification result: {}", message);
        model.addAttribute("message", message);
        model.addAttribute("status", status);
        return "result";
    }

}
