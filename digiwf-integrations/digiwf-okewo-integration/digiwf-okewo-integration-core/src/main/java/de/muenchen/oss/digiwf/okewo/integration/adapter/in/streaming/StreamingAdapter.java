package de.muenchen.oss.digiwf.okewo.integration.adapter.in.streaming;

import de.muenchen.oss.digiwf.message.common.MessageConstants;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import de.muenchen.oss.digiwf.okewo.integration.application.port.in.GetPersonErweitertInPort;
import de.muenchen.oss.digiwf.okewo.integration.application.port.in.GetPersonInPort;
import de.muenchen.oss.digiwf.okewo.integration.application.port.in.SearchPersonErweitertInPort;
import de.muenchen.oss.digiwf.okewo.integration.application.port.in.SearchPersonInPort;
import de.muenchen.oss.digiwf.okewo.integration.client.model.*;
import de.muenchen.oss.digiwf.okewo.integration.domain.model.request.OkEwoOmBasedRequest;
import de.muenchen.oss.digiwf.okewo.integration.domain.model.request.OkEwoSearchPersonExtendedRequest;
import de.muenchen.oss.digiwf.okewo.integration.domain.model.request.OkEwoSearchPersonRequest;
import de.muenchen.oss.digiwf.okewo.integration.domain.model.request.OrdnungsmerkmalDto;
import de.muenchen.oss.digiwf.okewo.integration.domain.model.response.OkEwoErrorDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
public class StreamingAdapter {

    private static final String RESPONSE = "response";

    private final ProcessApi processApi;
    private final ErrorApi errorApi;

    private final GetPersonInPort getPersonInPort;
    private final GetPersonErweitertInPort getPersonErweitertInPort;
    private final SearchPersonInPort searchPersonInPort;
    private final SearchPersonErweitertInPort searchPersonErweitertInPort;

    /**
     * The Consumer expects an {@link OkEwoOmBasedRequest} which represents an {@link OrdnungsmerkmalDto} for OK.EWO.
     * <p>
     * After successfully requesting OK.EWO a JSON representing a {@link Person} is returned.
     * <p>
     * In case of an error the error message is returned as a JSON representing {@link OkEwoErrorDto}.
     */
    public Consumer<Message<OkEwoOmBasedRequest>> getPerson() {
        return message -> {
            log.debug("Processing new request \"getPerson\" from eventbus: {}", message);
            val payload = message.getPayload();
            val headers = message.getHeaders();
            val request = payload.getRequest();
            try {
                val response = getPersonInPort.getPerson(request.getOrdnungsmerkmal());
                Map<String, Object> result = Map.of(RESPONSE, response);
                this.correlateProcessMessage(headers, result);
            } catch (Exception e) {
                errorApi.handleIncident(headers, new IncidentError(e.getMessage()));
            }

        };
    }

    /**
     * The Consumer expects a {@link OkEwoOmBasedRequest} which represents the {@link SuchePersonAnfrage} for OK.EWO.
     * <p>
     * After successfully requesting OK.EWO a JSON representing a {@link SuchePersonAntwort} is returned.
     * <p>
     * In case of an error the error message is returned as a JSON representing {@link OkEwoErrorDto}.
     */
    public Consumer<Message<OkEwoSearchPersonRequest>> searchPerson() {
        return message -> {
            log.debug("Processing new request \"searchPerson\" from eventbus: {}", message);
            val payload = message.getPayload();
            val headers = message.getHeaders();

            try {
                val response = searchPersonInPort.searchPerson(payload.getRequest());
                Map<String, Object> result = Map.of(RESPONSE, response);
                this.correlateProcessMessage(headers, result);
            } catch (Exception e) {
                errorApi.handleIncident(headers, new IncidentError(e.getMessage()));
            }
        };
    }


    /**
     * The Consumer expects an {@link OkEwoOmBasedRequest} which represents an {@link OrdnungsmerkmalDto} for OK.EWO.
     * <p>
     * After successfully requesting OK.EWO a JSON representing a {@link de.muenchen.oss.digiwf.okewo.integration.client.model.PersonErweitert} is returned.
     * <p>
     * In case of an error the error message is returned as a JSON representing {@link OkEwoErrorDto}.
     */
    public Consumer<Message<OkEwoOmBasedRequest>> getPersonErweitert() {
        return message -> {
            log.debug("Processing new request \"getPersonErweitert\" from eventbus: {}", message);
            val payload = message.getPayload();
            val headers = message.getHeaders();

            val request = payload.getRequest();
            try {
                val response = getPersonErweitertInPort.getPerson(request.getOrdnungsmerkmal());
                Map<String, Object> result = Map.of(RESPONSE, response);
                this.correlateProcessMessage(headers, result);
            } catch (Exception e) {
                errorApi.handleIncident(headers, new IncidentError(e.getMessage()));
            }
        };
    }


    /**
     * The Consumer expects a {@link OkEwoOmBasedRequest} which represents the {@link SuchePersonerweitertAnfrage} for OK.EWO.
     * <p>
     * After successfully requesting OK.EWO a JSON representing a {@link SuchePersonerweitertAntwort} is returned.
     * <p>
     * In case of an error the error message is returned as a JSON representing {@link OkEwoErrorDto}.
     */
    public Consumer<Message<OkEwoSearchPersonExtendedRequest>> searchPersonErweitert() {
        return message -> {
            log.debug("Processing new request \"searchPersonErweitert\" from eventbus: {}", message);
            val payload = message.getPayload();
            val headers = message.getHeaders();
            try {
                val response = searchPersonErweitertInPort.searchPerson(payload.getRequest());
                Map<String, Object> result = Map.of(RESPONSE, response);
                this.correlateProcessMessage(headers, result);
            } catch (Exception e) {
                errorApi.handleIncident(headers, new IncidentError(e.getMessage()));
            }
        };
    }

    public void correlateProcessMessage(@NonNull MessageHeaders headers, Map<String, Object> payload) {
        final String processInstanceId = Objects.requireNonNull(headers.get(MessageConstants.DIGIWF_PROCESS_INSTANCE_ID)).toString();
        final String integrationName = Objects.requireNonNull(headers.get(MessageConstants.DIGIWF_INTEGRATION_NAME)).toString();
        final String type = Objects.requireNonNull(headers.get(MessageConstants.TYPE)).toString();
        if (payload == null) {
            payload = new HashMap<>();
        }
        this.processApi.correlateMessage(processInstanceId, type, integrationName, payload);
    }
}
