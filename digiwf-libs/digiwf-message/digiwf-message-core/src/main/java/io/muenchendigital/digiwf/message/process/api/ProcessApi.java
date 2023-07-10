package io.muenchendigital.digiwf.message.process.api;

import java.util.Map;

/**
 * Interface representing the process API for starting, correlating, and handling incidents and technical errors in processes.
 */
public interface ProcessApi {

    /**
     * Starts a new process with the given process key and variables.
     * @param processKey The process key of the process to be started.
     * @param variables The variables to be passed to the process.
     * @return true if the process was successfully started, false otherwise.
     */
    boolean startProcess(String processKey, Map<String, Object> variables);

    /**
     * Starts a new process with the given process key, variables, and file context.
     * @param processKey The process key of the process to be started.
     * @param variables The variables to be passed to the process.
     * @param fileContext The file context of the process to be started.
     * @return true if the process was successfully started, false otherwise.
     */
    boolean startProcess(String processKey, Map<String, Object> variables, String fileContext);

    /**
     * Correlates a message with the specified process instance ID and payload variables.
     * @param processInstanceId The ID of the process instance to correlate the message with.
     * @param messageName The name of the message to be correlated.
     * @param payloadVariables The payload variables to be included in the message correlation.
     * @return true if the message was successfully correlated, false otherwise.
     */

    boolean correlateMessage(String processInstanceId, String messageName, Map<String, Object> payloadVariables);

}
