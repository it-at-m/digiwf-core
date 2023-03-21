package io.muenchendigital.digiwf.util;

import io.muenchendigital.digiwf.message.process.api.out.CorrelateMessagePort;
import io.muenchendigital.digiwf.message.process.api.out.IncidentPort;
import io.muenchendigital.digiwf.message.process.api.out.StartProcessPort;
import io.muenchendigital.digiwf.message.process.api.out.TechnicalErrorPort;
import io.muenchendigital.digiwf.message.process.impl.dto.CorrelateMessageDto;
import io.muenchendigital.digiwf.message.process.impl.dto.StartProcessDto;
import io.muenchendigital.digiwf.message.process.impl.dto.TechnicalErrorDto;
import io.muenchendigital.digiwf.message.process.impl.model.Message;

public class DummyProcessPort  implements StartProcessPort, CorrelateMessagePort, IncidentPort, TechnicalErrorPort {

    @Override
    public boolean sendCorrelateMessage(final Message<CorrelateMessageDto> message, final String destination) {
        return true;
    }

    @Override
    public boolean sendIncidentMessage(final Message<Object> message, final String destination) {
        return true;
    }

    @Override
    public boolean startProcess(final Message<StartProcessDto> message, final String destination) {
        return true;
    }

    @Override
    public boolean sendTechnicalErrorMessage(final Message<TechnicalErrorDto> message, final String destination) {
        return true;
    }
}
