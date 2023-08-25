package io.muenchendigital.digiwf.okverkehr.integration.api.streaming;

import io.muenchendigital.digiwf.okverkehr.integration.api.dto.request.GetHalterRequestDto;
import io.muenchendigital.digiwf.okverkehr.integration.api.dto.request.OkVerkehrEventDto;
import io.muenchendigital.digiwf.okverkehr.integration.api.dto.response.OkVerkehrErrorDto;
import io.muenchendigital.digiwf.okverkehr.integration.gen.model.HalterPersonAnfrage;
import io.muenchendigital.digiwf.okverkehr.integration.gen.model.HalterPersonAntwort;
import io.muenchendigital.digiwf.okverkehr.integration.service.OkVerkehrHalterService;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.service.CorrelateMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.Map;
import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class OkVerkehrStreamingEventListener {

    private static final String RESPONSE = "response";

    private final CorrelateMessageService correlateMessageService;

    private final OkVerkehrHalterService okVerkehrHalterService;

    /**
     * The Consumer expects an {@link OkVerkehrEventDto} which represents an {@link HalterPersonAnfrage} for OK.Verkehr.
     * <p>
     * After successfully requesting OK.Verkehr a JSON representing a {@link HalterPersonAntwort} is returned.
     * <p>
     * In case of an error the error message is returned as a JSON representing {@link OkVerkehrErrorDto}.
     */
    @Bean
    public Consumer<Message<OkVerkehrEventDto>> getHalter() {
        return message -> {
            log.debug(message.toString());

            final var getHalterRequestDto = (GetHalterRequestDto) message.getPayload().getRequest();

            Object okVerkehrResult;
            try {
                okVerkehrResult = this.okVerkehrHalterService.getHalter(getHalterRequestDto.getHalterPersonAnfrage());
            } catch (final Exception exception) {
                okVerkehrResult = new OkVerkehrErrorDto(exception.getMessage());
            }

            this.correlateMessageService.sendCorrelateMessage(
                    message.getHeaders(),
                    Map.of(RESPONSE, okVerkehrResult)
            );
        };
    }

}
