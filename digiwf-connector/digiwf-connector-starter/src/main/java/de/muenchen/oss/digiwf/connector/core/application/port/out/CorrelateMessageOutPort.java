package de.muenchen.oss.digiwf.connector.core.application.port.out;

import de.muenchen.oss.digiwf.connector.core.domain.MessageCorrelation;


public interface CorrelateMessageOutPort {

    /**
     * Correlate a message
     *
     * @param correlateMessage correlation parameters
     */
    void correlateMessage(MessageCorrelation correlateMessage);

}
