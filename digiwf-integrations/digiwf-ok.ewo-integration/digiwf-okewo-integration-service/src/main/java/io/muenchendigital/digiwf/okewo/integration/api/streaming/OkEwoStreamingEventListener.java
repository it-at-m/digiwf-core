package io.muenchendigital.digiwf.okewo.integration.api.streaming;

import io.muenchendigital.digiwf.okewo.integration.api.dto.request.OkEwoEventDto;
import io.muenchendigital.digiwf.okewo.integration.api.dto.request.OrdnungsmerkmalDto;
import io.muenchendigital.digiwf.okewo.integration.api.dto.request.SearchPersonErweitertRequestDto;
import io.muenchendigital.digiwf.okewo.integration.api.dto.request.SearchPersonRequestDto;
import io.muenchendigital.digiwf.okewo.integration.api.dto.response.OkEwoErrorDto;
import io.muenchendigital.digiwf.okewo.integration.gen.model.Person;
import io.muenchendigital.digiwf.okewo.integration.gen.model.PersonErweitert;
import io.muenchendigital.digiwf.okewo.integration.gen.model.SuchePersonAnfrage;
import io.muenchendigital.digiwf.okewo.integration.gen.model.SuchePersonAntwort;
import io.muenchendigital.digiwf.okewo.integration.gen.model.SuchePersonerweitertAnfrage;
import io.muenchendigital.digiwf.okewo.integration.gen.model.SuchePersonerweitertAntwort;
import io.muenchendigital.digiwf.okewo.integration.service.OkEwoPersonErweitertService;
import io.muenchendigital.digiwf.okewo.integration.service.OkEwoPersonService;
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
public class OkEwoStreamingEventListener {

    private static final String RESPONSE = "response";

    private final CorrelateMessageService correlateMessageService;

    private final OkEwoPersonService okEwoPersonService;

    private final OkEwoPersonErweitertService okEwoPersonErweitertService;

    /**
     * The Consumer expects an {@link OkEwoEventDto} which represents an {@link OrdnungsmerkmalDto} for OK.EWO.
     * <p>
     * After successfully requesting OK.EWO a JSON representing a {@link Person} is returned.
     * <p>
     * In case of an error the error message is returned as a JSON representing {@link OkEwoErrorDto}.
     */
    @Bean
    public Consumer<Message<OkEwoEventDto>> getPerson() {
        return message -> {
            log.debug(message.toString());

            final var ordnungsmerkmal = (OrdnungsmerkmalDto) message.getPayload().getRequest();

            Object ewoResult;
            try {
                ewoResult = this.okEwoPersonService.getPerson(ordnungsmerkmal.getOrdnungsmerkmal());
            } catch (final Exception exception) {
                ewoResult = new OkEwoErrorDto(exception.getMessage());
            }

            this.correlateMessageService.sendCorrelateMessage(
                    message.getHeaders(),
                    Map.of(RESPONSE, ewoResult)
            );
        };
    }

    /**
     * The Consumer expects a {@link OkEwoEventDto} which represents the {@link SuchePersonAnfrage} for OK.EWO.
     * <p>
     * After successfully requesting OK.EWO a JSON representing a {@link SuchePersonAntwort} is returned.
     * <p>
     * In case of an error the error message is returned as a JSON representing {@link OkEwoErrorDto}.
     */
    @Bean
    public Consumer<Message<OkEwoEventDto>> searchPerson() {
        return message -> {
            log.debug(message.toString());

            final var searchPersonRequestDto = (SearchPersonRequestDto) message.getPayload().getRequest();

            Object ewoResult;
            try {
                ewoResult = this.okEwoPersonService.searchPerson(searchPersonRequestDto.getSearchPerson());
            } catch (final Exception exception) {
                ewoResult = new OkEwoErrorDto(exception.getMessage());
            }

            this.correlateMessageService.sendCorrelateMessage(
                    message.getHeaders(),
                    Map.of(RESPONSE, ewoResult)
            );
        };
    }

    /**
     * The Consumer expects an {@link OkEwoEventDto} which represents an {@link OrdnungsmerkmalDto} for OK.EWO.
     * <p>
     * After successfully requesting OK.EWO a JSON representing a {@link PersonErweitert} is returned.
     * <p>
     * In case of an error the error message is returned as a JSON representing {@link OkEwoErrorDto}.
     */
    @Bean
    public Consumer<Message<OkEwoEventDto>> getPersonErweitert() {
        return message -> {
            log.debug(message.toString());

            final var ordnungsmerkmal = (OrdnungsmerkmalDto) message.getPayload().getRequest();

            Object ewoResult;
            try {
                ewoResult = this.okEwoPersonErweitertService.getPerson(ordnungsmerkmal.getOrdnungsmerkmal());
            } catch (final Exception exception) {
                ewoResult = new OkEwoErrorDto(exception.getMessage());
            }

            this.correlateMessageService.sendCorrelateMessage(
                    message.getHeaders(),
                    Map.of(RESPONSE, ewoResult)
            );
        };
    }

    /**
     * The Consumer expects a {@link OkEwoEventDto} which represents the {@link SuchePersonerweitertAnfrage} for OK.EWO.
     * <p>
     * After successfully requesting OK.EWO a JSON representing a {@link SuchePersonerweitertAntwort} is returned.
     * <p>
     * In case of an error the error message is returned as a JSON representing {@link OkEwoErrorDto}.
     */
    @Bean
    public Consumer<Message<OkEwoEventDto>> searchPersonErweitert() {
        return message -> {
            log.debug(message.toString());

            final var searchPersonErweitertRequestDto = (SearchPersonErweitertRequestDto) message.getPayload().getRequest();

            Object ewoResult;
            try {
                ewoResult = this.okEwoPersonErweitertService.searchPerson(searchPersonErweitertRequestDto.getSearchPersonErweitert());
            } catch (final Exception exception) {
                ewoResult = new OkEwoErrorDto(exception.getMessage());
            }

            this.correlateMessageService.sendCorrelateMessage(
                    message.getHeaders(),
                    Map.of(RESPONSE, ewoResult)
            );
        };
    }

}
