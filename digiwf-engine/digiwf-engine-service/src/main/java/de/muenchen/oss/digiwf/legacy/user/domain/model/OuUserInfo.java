/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.legacy.user.domain.model;

import lombok.Data;

/**
 * The class is used to map the uid associated by the ou.
 *
 * @author externer.dl.horn
 */
@Data
public class OuUserInfo {

    /**
     * Uid (username) of the user.
     */
    private String uid;

}

