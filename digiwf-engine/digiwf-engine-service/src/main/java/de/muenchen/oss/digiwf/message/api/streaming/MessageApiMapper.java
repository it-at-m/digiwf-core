package de.muenchen.oss.digiwf.message.api.streaming;

import de.muenchen.oss.digiwf.message.domain.model.CorrelateMessage;
import org.mapstruct.Mapper;

/**
 * Map message domain objects to transport objects
 */
@Mapper
public interface MessageApiMapper {

    CorrelateMessage map(final CorrelateMessageTOV01 obj);

}
