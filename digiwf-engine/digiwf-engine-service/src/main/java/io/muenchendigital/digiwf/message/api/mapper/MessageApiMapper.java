package io.muenchendigital.digiwf.message.api.mapper;

import io.muenchendigital.digiwf.message.api.transport.CorrelateMessageTOV01;
import io.muenchendigital.digiwf.message.domain.model.CorrelateMessage;
import org.mapstruct.Mapper;

/**
 * Map message domain objects to transport objects
 */
@Mapper
public interface MessageApiMapper {

    CorrelateMessage map(final CorrelateMessageTOV01 obj);

}
