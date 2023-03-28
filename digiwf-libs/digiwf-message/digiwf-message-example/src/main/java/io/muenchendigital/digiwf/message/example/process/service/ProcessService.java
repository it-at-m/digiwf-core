package io.muenchendigital.digiwf.message.example.process.service;

import io.muenchendigital.digiwf.message.example.process.dto.ProcessMessageDto;
import io.muenchendigital.digiwf.message.example.process.dto.StartProcessDto;
import io.muenchendigital.digiwf.message.process.api.ProcessApi;
import io.muenchendigital.digiwf.message.process.api.error.BpmnError;
import io.muenchendigital.digiwf.message.process.api.error.IncidentError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProcessService {

    private final ProcessApi processApi;

    public void startProcess(final StartProcessDto startProcessDto) {
        this.processApi.startProcess(startProcessDto.getKey(), startProcessDto.getVariables());
    }

    public void correlateMessage(final ProcessMessageDto messageDto) throws IncidentError, BpmnError {
        if (messageDto.getProcessInstanceId() == null || messageDto.getProcessInstanceId().isEmpty()) {
            // incident occurred
            throw new IncidentError("Can not correlate message. ProcessInstanceId is missing.");
        }
        if (messageDto.getVariables().isEmpty()) {
            throw new BpmnError("400", "No variables defined.");
        }
        this.processApi.correlateMessage(messageDto.getProcessInstanceId(), messageDto.getMessageName(), messageDto.getVariables());
    }

}
