package io.muenchendigital.digiwf.integration.core.api;

import io.muenchendigital.digiwf.integration.core.impl.Integration;

/**
 * Interface to execute an integration.
 */
public interface IntegrationExecuteApi {

    /**
     * Registers an integration.
     *
     * @param integration integration to be registered
     */
    void register(final Integration integration);

    /**
     * Executes an integration.
     *
     * @param type type of the integration
     * @param data data to be processed
     * @return result of the integration
     */
    Object execute(final String type, final Object data);

}
