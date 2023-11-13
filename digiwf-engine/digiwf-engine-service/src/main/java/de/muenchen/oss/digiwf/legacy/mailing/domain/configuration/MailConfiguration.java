/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.mailing.domain.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration for the mail.
 *
 * @author externer.dl.horn
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(MailProperties.class)
public class MailConfiguration {

    private final MailProperties mailProperties;

    /**
     * Configures the {@link JavaMailSender}
     *
     * @return configured JavaMailSender
     */
    @Bean
    @Profile("!no-mail")
    public JavaMailSender getJavaMailSender() throws MessagingException {
        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.mailProperties.getHost());
        mailSender.setPort(this.mailProperties.getPort());
        mailSender.setProtocol(this.mailProperties.getProtocol());
        mailSender.setUsername(this.mailProperties.getUsername());
        mailSender.setPassword(this.mailProperties.getPassword());

        final Properties props = mailSender.getJavaMailProperties();
        props.putAll(this.mailProperties.getProperties());
        mailSender.setJavaMailProperties(props);
        mailSender.testConnection();
        return mailSender;
    }

    /**
     * Configure dummy {@link JavaMailSender}
     *
     * @return dummy JavaMailSender
     */
    @Bean
    @Profile("no-mail")
    public JavaMailSender getDummyJavaMailSender() {
        return new JavaMailSender() {

            @Override
            public void send(final SimpleMailMessage simpleMailMessage) throws MailException {

            }

            @Override
            public void send(final SimpleMailMessage... simpleMailMessages) throws MailException {

            }

            @Override
            public MimeMessage createMimeMessage() {
                return null;
            }

            @Override
            public MimeMessage createMimeMessage(final InputStream inputStream) throws MailException {
                return null;
            }

            @Override
            public void send(final MimeMessage mimeMessage) throws MailException {

            }

            @Override
            public void send(final MimeMessage... mimeMessages) throws MailException {

            }

            @Override
            public void send(final MimeMessagePreparator mimeMessagePreparator) throws MailException {

            }

            @Override
            public void send(final MimeMessagePreparator... mimeMessagePreparators) throws MailException {

            }
        };
    }

}
