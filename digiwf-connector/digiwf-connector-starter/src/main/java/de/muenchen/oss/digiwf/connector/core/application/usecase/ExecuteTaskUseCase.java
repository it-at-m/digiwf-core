package de.muenchen.oss.digiwf.connector.core.application.usecase;

import de.muenchen.oss.digiwf.connector.core.application.port.in.ExecuteTaskInPort;
import de.muenchen.oss.digiwf.connector.core.application.port.out.EmitEventOutPort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@Validated
@RequiredArgsConstructor
class ExecuteTaskUseCase implements ExecuteTaskInPort {

    private final EmitEventOutPort emitEventOutPort;

    @Override
    public void executeTask(@Valid ExecuteTaskCommand command) {
        log.info("Execute task with command {}", command);

        if (StringUtils.isNotBlank(command.getMessageName())) {
            emitEventOutPort.emitEvent(command.getMessageName(), command.getDestination(), command.getType(), command.getInstanceId(), command.getData());
        } else {
            emitEventOutPort.emitEvent(command.getDestination(), command.getType(), command.getInstanceId(), command.getData());
        }
    }
}
