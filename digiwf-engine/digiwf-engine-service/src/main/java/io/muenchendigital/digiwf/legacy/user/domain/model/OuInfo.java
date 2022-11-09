/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.legacy.user.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * The uids associated with a ou.
 *
 * @author externer.dl.horn
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OuInfo {

    private List<String> uids = new ArrayList<>();

}
