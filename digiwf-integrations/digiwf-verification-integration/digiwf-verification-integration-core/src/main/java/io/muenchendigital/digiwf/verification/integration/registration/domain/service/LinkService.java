package io.muenchendigital.digiwf.verification.integration.registration.domain.service;

import io.muenchendigital.digiwf.verification.integration.verification.api.resource.VerificationController;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

/**
 * Service to create the verification link.
 */
@RequiredArgsConstructor
public class LinkService {

    private final String baseAddress;

    /**
     * Generates a unique link to the verification service.
     *
     * @param token     a unique token
     * @return  the verification link to the service
     */
    public String generateLink(final UUID token){
        return baseAddress +
                VerificationController.ENDPOINT_VERIFICATION +
                "?token=" + token.toString();
    }

}
