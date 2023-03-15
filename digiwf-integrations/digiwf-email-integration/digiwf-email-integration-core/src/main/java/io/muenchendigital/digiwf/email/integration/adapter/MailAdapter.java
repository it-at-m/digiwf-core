package io.muenchendigital.digiwf.email.integration.adapter;

import io.muenchendigital.digiwf.email.integration.application.port.SendMailPort;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.MessagingException;
import java.util.Map;
import java.util.Properties;

@RequiredArgsConstructor
public class MailAdapter implements SendMailPort {

    private final JavaMailSender mailSender;

    public MailAdapter(
        final String host,
        final Integer port,
        final String protocol,
        final String username,
        final String password,
        final Map<String, String> properties
    ) throws MessagingException {
        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setProtocol(protocol);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        final Properties props = mailSender.getJavaMailProperties();
        props.putAll(properties);
        mailSender.setJavaMailProperties(props);
        mailSender.testConnection();
        this.mailSender = mailSender;
    }

    @Override
    public void sendMail(final MimeMessagePreparator preparator) {
        this.mailSender.send(preparator);
    }

}
