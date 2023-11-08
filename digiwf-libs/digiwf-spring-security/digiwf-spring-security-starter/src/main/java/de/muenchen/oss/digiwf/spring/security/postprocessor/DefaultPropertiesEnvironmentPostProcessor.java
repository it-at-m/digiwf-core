package de.muenchen.oss.digiwf.spring.security.postprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

/**
 * This post processor is registered in spring.factories to be called by the "real" ConfigFileApplicationListener to register the properties from
 * {@code application-spring-security-starter.yaml} in the environment. We prefer this method over adding a configuration annotated with @{@link
 * org.springframework.context.annotation.PropertySource} because the latter registers its properties too late for @{@link
 * org.springframework.boot.autoconfigure.condition.ConditionalOnProperty} annotations to use them.
 */
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public final class DefaultPropertiesEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final String DEFAULT_PROPERTY_SOURCE_NAME = "defaultProperties";
    private static final String DEFAULT_PROPERTY_SOURCE_LOCATION = "application.yaml";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        try {
            environment.getPropertySources().addLast(new ResourcePropertySource(
                    DEFAULT_PROPERTY_SOURCE_NAME,
                    new ClassPathResource(DEFAULT_PROPERTY_SOURCE_LOCATION)
            ));
        } catch (IOException e) {
            throw new IllegalStateException("Failed to add default properties", e);
        }
    }
}
