package de.muenchen.oss.digiwf.message.example.process.api;

import de.muenchen.oss.digiwf.message.example.process.dto.ProcessMessageDto;
import de.muenchen.oss.digiwf.message.example.process.dto.StartProcessDto;
import de.muenchen.oss.digiwf.message.example.process.service.ProcessService;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
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
    private final ErrorApi errorApi;

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
            this.errorApi.handleBpmnError(processMessageDto.getProcessInstanceId(), ex.getErrorCode(), ex.getErrorMessage());
            return ResponseEntity.badRequest().build();
        }
    }

}
