package de.muenchen.oss.digiwf.email.configuration;

import de.muenchen.oss.digiwf.email.api.DigiwfEmailApi;
import de.muenchen.oss.digiwf.email.impl.DigiwfEmailApiImpl;
import de.muenchen.oss.digiwf.email.properties.CustomMailProperties;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.Properties;

@RequiredArgsConstructor
@EnableConfigurationProperties({MailProperties.class, CustomMailProperties.class})
public class DigiwfEmailAutoConfiguration {

    private final MailProperties mailProperties;
    private final CustomMailProperties customMailProperties;

    /**
     * Configures the {@link JavaMailSender}
     *
     * @return configured JavaMailSender
     */
    @Bean
    @ConditionalOnMissingBean
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

    @ConditionalOnMissingBean
    @Bean
    public DigiwfEmailApi digiwfEmailApi(final ResourceLoader resourceLoader, final JavaMailSender javaMailSender, final FreeMarkerConfigurer freeMarkerConfigurer) {
        return new DigiwfEmailApiImpl(javaMailSender, resourceLoader, freeMarkerConfigurer, this.customMailProperties.getFromAddress(), this.customMailProperties.getDefaultReplyToAddress());
    }

    @Bean
    @ConditionalOnMissingBean
    public FreeMarkerConfigurer freemarkerConfig() {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath("classpath:templates/");
        return freeMarkerConfigurer;
    }

}
