package io.muenchendigital.digiwf.email.integration.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.MessagingException;
import java.util.Map;
import java.util.Properties;

@Configuration
public class MailConfiguration {

    public JavaMailSender getJavaMailSender(
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
        return mailSender;
    }

}
