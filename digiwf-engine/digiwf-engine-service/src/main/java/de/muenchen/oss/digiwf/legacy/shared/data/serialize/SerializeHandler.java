/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.shared.data.serialize;

import de.muenchen.oss.digiwf.legacy.form.domain.model.FormField;

import java.util.Map;

/**
 * Serialize Handler
 * Modifies the origin value and adds parts
 *
 * @author externer.dl.horn
 */
public interface SerializeHandler {

    Boolean isResponsibleFor(final FormField formField);

    Map<String, Object> serialize(final String key, final Object value, final FormField field);

}
