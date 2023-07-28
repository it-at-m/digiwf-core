/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.dms.shared;

/**
 * Base Document Metadata object.
 * Used for storing metadata information to a specific field key.
 *
 * @author externer.dl.horn
 */
public interface BaseSchriftstueckeData {

    String getFieldKey();

    BaseSchriftstueck[] getSchriftstuecke();
}
