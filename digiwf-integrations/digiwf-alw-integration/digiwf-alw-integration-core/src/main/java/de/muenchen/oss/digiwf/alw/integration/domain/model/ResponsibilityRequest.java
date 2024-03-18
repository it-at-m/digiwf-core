package de.muenchen.oss.digiwf.alw.integration.domain.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Request for responsibility.
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ResponsibilityRequest {

    /**
     * The AZR (Ausländerzentralregister) number of an individual. Must be exactly 12 digits long as per the specified pattern. If the pattern is not matched,
     * an error message indicates the number is invalid.
     */
    @Pattern(regexp = "^\\d{12}$", message = "AZR-Number is invalid; it must contain 12 digits.")
    @NotEmpty(message = "The AZR-Number is required and cannot be null or empty.")
    private String azrNummer;
}
