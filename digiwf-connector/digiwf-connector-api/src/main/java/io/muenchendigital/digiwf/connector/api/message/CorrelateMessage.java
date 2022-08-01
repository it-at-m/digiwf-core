package io.muenchendigital.digiwf.connector.api.message;

import java.util.Map;

public interface CorrelateMessage {

    String getProcessInstanceId();

    String getMessageName();

    String getBusinessKey();

    Map<String, Object> getPayloadVariables();

    Map<String, Object> getPayloadVariablesLocal();
}
