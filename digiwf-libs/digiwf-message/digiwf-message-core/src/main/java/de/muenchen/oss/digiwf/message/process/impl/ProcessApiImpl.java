package de.muenchen.oss.digiwf.message.process.impl;

import de.muenchen.oss.digiwf.message.core.api.MessageApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.message.process.impl.dto.CorrelateMessageDto;
import de.muenchen.oss.digiwf.message.process.impl.dto.StartProcessDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.*;

/**
 * Default implementation of {@link ProcessApi}.
 */
@Slf4j
@RequiredArgsConstructor
public class ProcessApiImpl implements ProcessApi {

    private final MessageApi messageApi;

    private final String correlateMessageDestination;
    private final String startProcessDestination;

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

}
