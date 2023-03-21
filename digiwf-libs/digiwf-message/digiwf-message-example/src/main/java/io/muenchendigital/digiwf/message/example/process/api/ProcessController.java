package io.muenchendigital.digiwf.message.example.process.api;

import io.muenchendigital.digiwf.message.common.error.BpmnError;
import io.muenchendigital.digiwf.message.common.error.IncidentError;
import io.muenchendigital.digiwf.message.example.process.dto.ProcessMessageDto;
import io.muenchendigital.digiwf.message.example.process.dto.StartProcessDto;
import io.muenchendigital.digiwf.message.example.process.service.ProcessService;
import io.muenchendigital.digiwf.message.process.api.ProcessApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/process")
@Slf4j
public class ProcessController {

    private final ProcessService processService;
    private final ProcessApi processApi;

    @PostMapping("/start")
    public ResponseEntity startProcess(@RequestBody final StartProcessDto startProcessDto) {
        this.processService.startProcess(startProcessDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/correlate")
    public ResponseEntity correlateMessage(@RequestBody final ProcessMessageDto processMessageDto) throws BpmnError {
        // Note: The error handling is applied to the controller for demonstration purposes only.
        // Usually it's implemented for async apis (like spring cloud stream consumers).
        try {
            this.processService.correlateMessage(processMessageDto);
            return ResponseEntity.ok().build();
        } catch (final BpmnError ex) {
            log.warn("Handle technical error");
            this.processApi.handleBpmnError(processMessageDto.getProcessInstanceId(), ex.getErrorCode(), ex.getErrorMessage());
            return ResponseEntity.badRequest().build();
        } catch (final IncidentError ex) {
            log.warn("Handle incident");
            this.processApi.handleIncident(processMessageDto.getProcessInstanceId(), processMessageDto.getMessageName(), ex.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

}
