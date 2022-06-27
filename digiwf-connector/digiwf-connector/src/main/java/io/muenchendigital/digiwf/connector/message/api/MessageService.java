package io.muenchendigital.digiwf.connector.message.api;

/**
 * Service to correlate messages in digiwf.
 */
public interface MessageService {

    /**
     * Correlate a message
     *
     * @param correlateMessage correlation parameters
     */
    void correlateMessage(CorrelateMessage correlateMessage);

}
