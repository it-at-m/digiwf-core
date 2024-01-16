package de.muenchen.oss.digiwf.example.integration.core.application.out;

import java.util.Map;

public interface CorrelateMessagePort {

    void correlateMessage(final String processInstanceId, final String type, final String integrationName, final Map<String, Object> message);

}
