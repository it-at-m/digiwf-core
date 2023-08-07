/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.mailing.process;

import io.holunda.camunda.bpm.data.factory.VariableFactory;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;

/**
 * Process variables for the mail template.
 *
 * @author externer.dl.horn
 */
public class MailingVariables {

    public static final VariableFactory<String> SUBJECT = stringVariable("MAILING_SUBJECT");
    public static final VariableFactory<String> RECEIVERS = stringVariable("MAILING_RECEIVERS");
    public static final VariableFactory<String> BODY = stringVariable("MAILING_BODY");
    public static final VariableFactory<String> REPLY_TO = stringVariable("MAILING_REPLY_TO");
    public static final VariableFactory<String> ATTACHMENT_GUID = stringVariable("MAILING_ATTACHMENT_GUID");
    public static final VariableFactory<String> ATTACHMENT_NAME = stringVariable("MAILING_ATTACHMENT_NAME");
    public static final VariableFactory<String> TEMPLATE_ID = stringVariable("TEMPLATE_ID");
    public static final VariableFactory<String> SENDER = stringVariable("MAILING_SENDER");

    public static final VariableFactory<String> BOTTOM_TEXT = stringVariable("MAILING_BOTTOM");
    public static final VariableFactory<String> LINK_TEXT = stringVariable("MAILING_LINK_TEXT");
    public static final VariableFactory<String> LINK_URL = stringVariable("MAILING_LINK_URL");

}
