package de.muenchen.oss.digiwf.cosys.integration.application.port.out;

import java.util.Map;

public interface CorrelateMessagePort {

    void correlateMessage(final String processInstanceId, final String messageName, final Map<String, Object> message);

}
