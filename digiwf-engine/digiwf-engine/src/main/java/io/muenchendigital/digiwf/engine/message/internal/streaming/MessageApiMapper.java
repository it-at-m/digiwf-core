package io.muenchendigital.digiwf.engine.message.internal.streaming;

import io.muenchendigital.digiwf.engine.message.api.CorrelateMessageDto;
import io.muenchendigital.digiwf.engine.message.internal.impl.model.CorrelateMessage;
import org.mapstruct.Mapper;

/**
 * Map message domain objects to transport objects
 */
@Mapper
public interface MessageApiMapper {

    CorrelateMessage map(final CorrelateMessageDto obj);

}
