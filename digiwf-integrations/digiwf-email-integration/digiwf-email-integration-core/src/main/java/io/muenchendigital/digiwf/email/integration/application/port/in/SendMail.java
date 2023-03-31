package io.muenchendigital.digiwf.email.integration.application.port.in;

import io.muenchendigital.digiwf.email.integration.model.Mail;

public interface SendMail {

    void sendMail(final String processInstanceId, final Mail mail);

}
