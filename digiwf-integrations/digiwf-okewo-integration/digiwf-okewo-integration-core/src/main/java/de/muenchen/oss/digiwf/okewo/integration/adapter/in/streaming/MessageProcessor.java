package de.muenchen.oss.digiwf.okewo.integration.adapter.in.streaming;

import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import de.muenchen.oss.digiwf.okewo.integration.application.in.GetPersonErweitertInPort;
import de.muenchen.oss.digiwf.okewo.integration.application.in.GetPersonInPort;
import de.muenchen.oss.digiwf.okewo.integration.application.in.SearchPersonErweitertInPort;
import de.muenchen.oss.digiwf.okewo.integration.application.in.SearchPersonInPort;
import de.muenchen.oss.digiwf.okewo.integration.application.out.IntegrationOutPort;
import de.muenchen.oss.digiwf.okewo.integration.client.model.*;
import de.muenchen.oss.digiwf.okewo.integration.domain.model.request.OkEwoOmBasedRequest;
import de.muenchen.oss.digiwf.okewo.integration.domain.model.request.OkEwoSearchPersonExtendedRequest;
import de.muenchen.oss.digiwf.okewo.integration.domain.model.request.OkEwoSearchPersonRequest;
import de.muenchen.oss.digiwf.okewo.integration.domain.model.request.OrdnungsmerkmalDto;
import de.muenchen.oss.digiwf.okewo.integration.domain.model.response.OkEwoErrorDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.Map;
import java.util.function.Consumer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MessageProcessor {

  private static final String RESPONSE = "response";

  private final IntegrationOutPort integration;

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
  @Bean
  public Consumer<Message<OkEwoOmBasedRequest>> getPerson() {
    return message -> {
      log.debug("Processing new request \"getPerson\" from eventbus: {}", message);
      val payload = message.getPayload();
      val headers = message.getHeaders();
      val request = payload.getRequest();
      try {
        val response = getPersonInPort.getPerson(request.getOrdnungsmerkmal());
        Map<String, Object> result = Map.of(RESPONSE, response);
        integration.correlateProcessMessage(headers, result);
      } catch (Exception e) {
        integration.handleIncident(headers, new IncidentError(e.getMessage()));
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
  @Bean
  public Consumer<Message<OkEwoSearchPersonRequest>> searchPerson() {
    return message -> {
      log.debug("Processing new request \"searchPerson\" from eventbus: {}", message);
      val payload = message.getPayload();
      val headers = message.getHeaders();

      try {
        val response = searchPersonInPort.searchPerson(payload.getRequest());
        Map<String, Object> result = Map.of(RESPONSE, response);
        integration.correlateProcessMessage(headers, result);
      } catch (Exception e) {
        integration.handleIncident(headers, new IncidentError(e.getMessage()));
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
  @Bean
  public Consumer<Message<OkEwoOmBasedRequest>> getPersonErweitert() {
    return message -> {
      log.debug("Processing new request \"getPersonErweitert\" from eventbus: {}", message);
      val payload = message.getPayload();
      val headers = message.getHeaders();

      val request = payload.getRequest();
      try {
        val response = getPersonErweitertInPort.getPerson(request.getOrdnungsmerkmal());
        Map<String, Object> result = Map.of(RESPONSE, response);
        integration.correlateProcessMessage(headers, result);
      } catch (Exception e) {
        integration.handleIncident(headers, new IncidentError(e.getMessage()));
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
  @Bean
  public Consumer<Message<OkEwoSearchPersonExtendedRequest>> searchPersonErweitert() {
    return message -> {
      log.debug("Processing new request \"searchPersonErweitert\" from eventbus: {}", message);
      val payload = message.getPayload();
      val headers = message.getHeaders();
      try {
        val response = searchPersonErweitertInPort.searchPerson(payload.getRequest());
        Map<String, Object> result = Map.of(RESPONSE, response);
        integration.correlateProcessMessage(headers, result);
      } catch (Exception e) {
        integration.handleIncident(headers, new IncidentError(e.getMessage()));
      }
    };
  }
}
