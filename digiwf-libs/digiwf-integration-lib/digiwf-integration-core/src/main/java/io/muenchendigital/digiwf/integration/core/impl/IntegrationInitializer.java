package io.muenchendigital.digiwf.integration.core.impl;

import io.muenchendigital.digiwf.integration.core.api.DigiwfIntegration;
import io.muenchendigital.digiwf.integration.core.api.IntegrationExecuteApi;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Initializes all integrations after the application has started using the spring application ready event.
 */
@Component
@RequiredArgsConstructor
public class IntegrationInitializer implements ApplicationContextAware {
    private ApplicationContext ctx;
    private final IntegrationExecuteApi integrationExecuteApi;

    /**
     * Initializes all integrations after the application has started using the spring application ready event.
     * All beans with the {@link DigiwfIntegration} annotation are registered as integrations.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initializeIntegrationsAfterStartup() {
        final List<Integration> integrations = this.getIntegrations();
        integrations.forEach(this.integrationExecuteApi::register);
    }

    /**
     * Sets the application context.
     *
     * @param applicationContext application context
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    /**
     * Helper method to get all integrations (using the {@link DigiwfIntegration} annotation) from the spring context.
     *
     * @return list of integrations
     */
    private List<Integration> getIntegrations() {
        final List<Integration> integrations = new ArrayList<>();
        final String[] beanDefinitionNames = this.ctx.getBeanDefinitionNames();
        // Iterate over the list of spring beans and get all methods annotated with the specific annotation
        for (final String beanDefinitionName : beanDefinitionNames) {
            final Object bean = this.ctx.getBean(beanDefinitionName);
            final Class<?> beanClass = bean.getClass();

            ReflectionUtils.doWithMethods(beanClass, method -> {
                // Check if the method is annotated with the specific annotation
                if (method.isAnnotationPresent(DigiwfIntegration.class)) {
                    final DigiwfIntegration annotInstance = method.getAnnotation(DigiwfIntegration.class);
                    integrations.add(this.buildIntegration(annotInstance, bean, method));
                }
            });
        }
        return integrations;
    }

    /**
     * Helper method to build an integration.
     *
     * @param integration integration annotation
     * @param bean        bean
     * @param method      method
     * @return integration
     */
    private Integration buildIntegration(final DigiwfIntegration integration, final Object bean, final Method method) {
        final Class<?>[] inputParameterTypes = method.getParameterTypes();

        if (inputParameterTypes.length > 1) {
            throw new IllegalArgumentException("Too many parameters");
        }

        final Class<?> inputParameter = inputParameterTypes.length == 0 ? null : inputParameterTypes[0];
        return new Integration(integration.type(), integration.timeout(), bean, method, inputParameter, method.getReturnType());
    }

}
