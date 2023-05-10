package io.muenchendigital.digiwf.email.integration.application.port.in;

import io.muenchendigital.digiwf.email.integration.model.Mail;

import javax.validation.Valid;

public interface SendMail {

    void sendMail(final String processInstanceId, @Valid final Mail mail);

}
