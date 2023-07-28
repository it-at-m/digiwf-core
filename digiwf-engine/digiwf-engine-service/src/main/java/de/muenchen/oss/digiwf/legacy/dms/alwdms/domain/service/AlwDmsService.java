/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.dms.alwdms.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.muenchen.oss.digiwf.legacy.dms.alwdms.domain.model.AlwMetadata;
import de.muenchen.oss.digiwf.legacy.dms.alwdms.domain.model.AlwSchriftstueck;
import de.muenchen.oss.digiwf.legacy.dms.alwdms.external.client.AlwDmsClient;
import de.muenchen.oss.digiwf.legacy.dms.alwdms.properties.AlwDmsProperties;
import de.muenchen.oss.digiwf.legacy.user.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

/**
 * Service to interact with the alw dms domain.
 *
 * @author externer.dl.horn
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AlwDmsService {

    private final AlwDmsClient client;
    private final UserService userService;
    private final AlwDmsProperties properties;

    /**
     * Load the metadata with the given user for the given url.
     *
     * @param user User to load the data
     * @param url  Url that contains the query information
     * @return metadata object
     */
    public AlwMetadata getContentMetadata(final String user, final String url) {
        val username = this.getUsername(user);

        try {
            final AlwMetadata metadata = this.client.readContentMetaData(username, this.parseUrl(url));
            metadata.setUrl(url);
            return metadata;
        } catch (final JsonProcessingException e) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("Metadata could no be read. User: %s Obj: %s", user, url));
        }
    }

    /**
     * Load the docuemnt for the given user and the given url.
     *
     * @param user User to load the document
     * @param url  Url that contains the data
     * @return Document
     */
    public AlwSchriftstueck readSchriftstueck(final String user, final String url) {
        val username = this.getUsername(user);

        try {
            return this.client.readSchriftstueck(username, this.parseUrl(url));
        } catch (final JsonProcessingException e) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("Schriftstueck could no be read. User: %s Obj: %s", user, url));
        }
    }

    // HELPER METHODS

    private String getUsername(final String user) {
        val defaultUser = this.properties.getDefaultUser();

        if (StringUtils.isNoneBlank(defaultUser)) {
            return this.userService.getUser(defaultUser).getUsername();
        }

        return this.userService.getUser(user).getUsername();
    }

    private String parseUrl(final String url) {
        //alwdmstest02.muenchen.de/fsc/mx/COO.2150.9400.2.66044
        return url.substring(url.lastIndexOf("/mx/") + 4);
    }

}
