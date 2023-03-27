package io.muenchendigital.digiwf.message.process.impl;

import io.muenchendigital.digiwf.message.core.api.MessageApi;
import io.muenchendigital.digiwf.message.process.api.ProcessApi;
import io.muenchendigital.digiwf.message.process.impl.dto.BpmnErrorDto;
import io.muenchendigital.digiwf.message.process.impl.dto.CorrelateMessageDto;
import io.muenchendigital.digiwf.message.process.impl.dto.StartProcessDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static io.muenchendigital.digiwf.message.common.MessageConstants.*;

/**
 * Default implementation of {@link ProcessApi}.
 */
@Slf4j
@RequiredArgsConstructor
public class ProcessApiImpl implements ProcessApi {

    private final MessageApi messageApi;

    private final String correlateMessageDestination;
    private final String startProcessDestination;
    private final String incidentDestination;
    private final String bpmnErrorDestination;

    private static final String CORRELATEMESSAGEV_01 = "correlatemessagev01";
    private static final String STARTPROCESS_V01 = "startProcessV01";

    /**
     * Starts a process with the given process key and variables by sending a message to the start process destination.
     * @param processKey The process key of the process to be started.
     * @param variables The variables to be passed to the process.
     * @return
     */
    @Override
    public boolean startProcess(final String processKey, final Map<String, Object> variables) {
        return this.startProcess(processKey, variables, null);
    }

    /**
     * Starts a process with the given process key and variables by sending a message to the start process destination.
     * @param processKey The process key of the process to be started.
     * @param variables The variables to be passed to the process.
     * @param fileContext The file context to be passed to the process.
     * @return
     */
    @Override
    public boolean startProcess(final String processKey, final Map<String, Object> variables, final String fileContext) {
        final StartProcessDto payload = StartProcessDto.builder()
                .key(processKey)
                .fileContext(fileContext)
                .data(variables)
                .build();
        return this.messageApi.sendMessage(payload, Map.of(TYPE, STARTPROCESS_V01), this.startProcessDestination);
    }

    /**
     * Correlates a message to the process matching the given process instance id.
     * The correlate message contains the process instance id, message name and variables.
     *
     * @param processInstanceId The process instance id of the process to be correlated.
     * @param messageName The message name to be correlated.
     * @param payloadVariables The variables to be passed to the process.
     * @return
     */
    @Override
    public boolean correlateMessage(final String processInstanceId, final String messageName, final Map<String, Object> payloadVariables) {
        final CorrelateMessageDto payload = CorrelateMessageDto.builder()
                .processInstanceId(processInstanceId)
                .messageName(messageName)
                .payloadVariables(payloadVariables)
                .build();
        final Map<String, Object> headers = Map.of(
                TYPE, CORRELATEMESSAGEV_01,
                DIGIWF_PROCESS_INSTANCE_ID, processInstanceId,
                DIGIWF_MESSAGE_NAME, messageName
        );
        return this.messageApi.sendMessage(payload, headers, this.correlateMessageDestination);
    }

    /**
     * Handles an incident by sending a message to the incident destination.
     * The incident message contains the process instance id, message name and error message.
     *
     * @param processInstanceId The process instance id of the process to be correlated.
     * @param messageName The message name to be correlated.
     * @param errorMessage The error message to be passed to the process.
     * @return
     */
    @Override
    public boolean handleIncident(final String processInstanceId, final String messageName, final String errorMessage) {
        log.error("Incident occured for process {} with error message {}", processInstanceId, errorMessage);
        final Map<String, Object> headers = Map.of(
                TYPE, this.incidentDestination,
                DIGIWF_PROCESS_INSTANCE_ID, processInstanceId,
                DIGIWF_MESSAGE_NAME, messageName
        );
        return this.messageApi.sendMessage(errorMessage, headers, this.incidentDestination);
    }

    /**
     * Handles a bpmn error by sending a message to the bpmn error destination.
     * The bpmn error message contains the process instance id, error code and error message.
     *
     * @param processInstanceId The process instance id of the process to be correlated.
     * @param errorCode The error code to be passed to the process.
     * @param errorMessage The error message to be passed to the process.
     * @return
     */
    @Override
    public boolean handleBpmnError(final String processInstanceId, final String errorCode, final String errorMessage) {
        log.warn("A technical error occured for process {} with error message {}", processInstanceId, errorMessage);
        final BpmnErrorDto payload = BpmnErrorDto.builder()
                .processInstanceId(processInstanceId)
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .messageName(this.bpmnErrorDestination)
                .build();
        return this.messageApi.sendMessage(payload, Map.of(TYPE, this.bpmnErrorDestination), this.bpmnErrorDestination);
    }

}
