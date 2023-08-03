/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.form.domain.validator;

import de.muenchen.oss.digiwf.legacy.form.domain.model.FormField;

/**
 * Validation handler interface for {@link FormField}
 *
 * @author externer.dl.horn
 */
public interface ValidationHandler {

    boolean validate(final Object value, final FormField field);

}
