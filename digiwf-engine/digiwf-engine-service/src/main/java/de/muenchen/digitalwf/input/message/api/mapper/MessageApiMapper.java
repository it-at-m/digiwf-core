package de.muenchen.digitalwf.input.message.api.mapper;

import de.muenchen.digitalwf.input.message.api.transport.CorrelateMessageTOV01;
import de.muenchen.digitalwf.input.message.domain.model.CorrelateMessage;
import org.mapstruct.Mapper;

/**
 * Map message domain objects to transport objects
 */
@Mapper
public interface MessageApiMapper {

    CorrelateMessage map(final CorrelateMessageTOV01 obj);

}
