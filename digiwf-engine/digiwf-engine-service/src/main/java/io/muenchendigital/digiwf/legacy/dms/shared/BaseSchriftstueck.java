/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.dms.shared;

/**
 * Base document object.
 *
 * @author externer.dl.horn
 */
public interface BaseSchriftstueck {

    byte[] getContent();

    String getExtension();

    String getName();
}
