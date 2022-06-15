/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.engine.security.api;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Each possible authority in this project is represented by an enum.
 * The enums are used within the {@link PagingAndSortingRepository}
 * in the annotation e.g. {@link PreAuthorize}.
 */
public enum AuthoritiesEnum {
    BACKEND_READ_THEENTITY,
    BACKEND_WRITE_THEENTITY,
    BACKEND_DELETE_THEENTITY,
    BACKEND_DEPLOY_RESOURCE
    // add your authorities here and also add these new authorities to sso-authorisation.json.
}
