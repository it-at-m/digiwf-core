package io.muenchendigital.digiwf.task.envprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;

import static java.util.Arrays.asList;

/**
 * This post processor is registered in spring.factories to be called by the "real" ConfigFileApplicationListener to register the properties from
 * {@code application-polyflow-kafka.yml} in the environment. We prefer this method over adding a configuration annotated with @{@link
 * org.springframework.context.annotation.PropertySource} because the latter registers its properties too late for @{@link
 * org.springframework.boot.autoconfigure.condition.ConditionalOnProperty} annotations to use them.
 * <p>
 * This class is effectively disabled if the {@code disable-polyflow-kafka} profile is active.
 */
@Order(Ordered.LOWEST_PRECEDENCE - 10)
public final class AxonKafkaPropertiesEnvironmentPostProcessor extends ConfigFileApplicationListener {

    public static final String PROFILE = "polyflow-kafka";
    public static final String PROFILE_DISABLED = "disable-" + PROFILE;

    public AxonKafkaPropertiesEnvironmentPostProcessor() {
        // the order must be defined right at construction time
        setSearchNames("application-" + PROFILE);
        setOrder(Ordered.LOWEST_PRECEDENCE - 10);
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        // Only load the axon-kafka specific properties if the disabling profile is NOT active
        // This results in an opt-out strategy (e.g. for local development)
        if (!asList(environment.getActiveProfiles()).contains(PROFILE_DISABLED)) {
            super.postProcessEnvironment(environment, application);
        }
    }
}
