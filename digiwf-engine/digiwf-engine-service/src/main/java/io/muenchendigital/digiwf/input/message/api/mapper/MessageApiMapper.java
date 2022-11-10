package io.muenchendigital.digiwf.input.message.api.mapper;

import io.muenchendigital.digiwf.input.message.api.transport.CorrelateMessageTOV01;
import io.muenchendigital.digiwf.input.message.domain.model.CorrelateMessage;
import org.mapstruct.Mapper;

/**
 * Map message domain objects to transport objects
 */
@Mapper
public interface MessageApiMapper {

    CorrelateMessage map(final CorrelateMessageTOV01 obj);

}
