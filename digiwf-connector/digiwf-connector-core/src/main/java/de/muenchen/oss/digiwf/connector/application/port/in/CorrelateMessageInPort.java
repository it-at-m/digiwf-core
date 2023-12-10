package de.muenchen.oss.digiwf.connector.application.port.in;

import de.muenchen.oss.digiwf.connector.domain.MessageCorrelation;


public interface CorrelateMessageInPort {

    /**
     * Correlate a message
     *
     * @param correlateMessage correlation parameters
     */
    void correlateMessage(MessageCorrelation correlateMessage);

}
