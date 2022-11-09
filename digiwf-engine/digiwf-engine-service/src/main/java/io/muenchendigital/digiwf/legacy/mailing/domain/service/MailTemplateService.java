/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.mailing.domain.service;

import lombok.RequiredArgsConstructor;

import lombok.val;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Service to handle mail templates.
 *
 * @author externer.dl.horn
 */
@Service
@RequiredArgsConstructor
public class MailTemplateService {

    private final ResourceLoader resourceLoader;

    /**
     * Get a mail template as utf-8 string.
     *
     * @return mail template
     */
    public String getMailTemplate() throws IOException {
        val resource = ResourcePatternUtils.getResourcePatternResolver(this.resourceLoader).getResource("classpath:bausteine/mail/template/mail-template.tpl");
        final byte[] bytes = this.asString(resource).getBytes(StandardCharsets.UTF_8);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Get the mail template with link as utf-8 string.
     *
     * @return mail template
     */
    public String getMailTemplateWithLink() throws IOException {
        val resource = ResourcePatternUtils.getResourcePatternResolver(this.resourceLoader)
                .getResource("classpath:bausteine/mail/templatewithlink/mail-template.tpl");
        final byte[] bytes = this.asString(resource).getBytes(StandardCharsets.UTF_8);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Get the default logo.
     *
     * @return default logo
     */
    public Resource getLogo() {
        return ResourcePatternUtils.getResourcePatternResolver(this.resourceLoader).getResource("classpath:bausteine/mail/email-logo.png");
    }

    /**
     * Get the logo as resource by path.
     *
     * @param mailLogoPath path to the logo
     * @return logo
     */
    public Resource getLogoByPath(final String mailLogoPath) throws IOException {
        return ResourcePatternUtils.getResourcePatternResolver(this.resourceLoader).getResource("classpath:" + mailLogoPath);
    }

    // helper methods

    private String asString(final Resource resource) throws IOException {
        try (final Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        }
    }

}
