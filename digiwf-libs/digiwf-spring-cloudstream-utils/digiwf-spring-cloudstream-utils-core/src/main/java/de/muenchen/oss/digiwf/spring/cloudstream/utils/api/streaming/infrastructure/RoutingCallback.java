package de.muenchen.oss.digiwf.spring.cloudstream.utils.api.streaming.infrastructure;

import de.muenchen.oss.digiwf.spring.cloudstream.utils.api.error.ErrorMessageDefaultListener;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.function.context.MessageRoutingCallback;
import org.springframework.messaging.Message;

import java.util.Map;

@RequiredArgsConstructor
@Deprecated
public class RoutingCallback implements MessageRoutingCallback {

    private final Map<String, String> typeMappings;

    /**
     * Router for messages.
     * Either routes the message to the corresponding function (if present), or, in case the TYPE header is unknown or unset,
     * to the corresponding error functions.
     * unknown or unset.
     * @param message incoming message
     * @return FunctionRoutingResult as described above
     */
    @Override
    public FunctionRoutingResult routingResult(final Message<?> message) {
        final String functionDefinition;

        if (message.getHeaders().containsKey(StreamingHeaders.TYPE)) {
            final String header = (String) message.getHeaders().get(StreamingHeaders.TYPE);
            functionDefinition = this.typeMappings.getOrDefault(header, ErrorMessageDefaultListener.FUNCTION_ROUTING_ERROR);
        } else {
            functionDefinition = ErrorMessageDefaultListener.MISSING_TYPE_HEADER_ERROR;
        }

        return new FunctionRoutingResult(functionDefinition);
    }

}
