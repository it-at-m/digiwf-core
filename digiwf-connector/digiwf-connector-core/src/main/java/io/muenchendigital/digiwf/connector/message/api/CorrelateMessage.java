package io.muenchendigital.digiwf.connector.message.api;

import java.util.Map;

public interface CorrelateMessage {

    String getProcessInstanceId();

    String getMessageName();

    String getBusinessKey();

    Map<String, Object> getPayloadVariables();

    Map<String, Object> getPayloadVariablesLocal();
}
