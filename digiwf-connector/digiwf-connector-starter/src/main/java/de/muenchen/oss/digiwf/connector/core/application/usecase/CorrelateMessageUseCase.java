package de.muenchen.oss.digiwf.connector.core.application.usecase;

import de.muenchen.oss.digiwf.connector.core.application.port.in.CorrelateMessageInPort;
import de.muenchen.oss.digiwf.connector.core.application.port.out.CorrelateMessageOutPort;
import de.muenchen.oss.digiwf.connector.core.domain.MessageCorrelation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@Validated
@RequiredArgsConstructor
class CorrelateMessageUseCase implements CorrelateMessageInPort {

    private final CorrelateMessageOutPort correlateMessageOutPort;

    @Override
    public void correlateMessage(MessageCorrelation messageCorrelation) {
        log.info("Received correlate message {}", messageCorrelation);
        correlateMessageOutPort.correlateMessage(messageCorrelation);
    }
}
