package de.muenchen.oss.digiwf.address.integration.adapter.in.streaming;

import de.muenchen.oss.digiwf.address.integration.adapter.in.streaming.dto.*;
import de.muenchen.oss.digiwf.address.integration.application.port.in.AddressGermanyInPort;
import de.muenchen.oss.digiwf.address.integration.application.port.in.AddressMunichInPort;
import de.muenchen.oss.digiwf.address.integration.application.port.in.StreetsMunichInPort;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.*;
import de.muenchen.oss.digiwf.address.integration.client.model.request.*;
import de.muenchen.oss.digiwf.address.integration.client.model.response.AddressDistancesModel;
import de.muenchen.oss.digiwf.message.common.MessageConstants;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Slf4j
public class StreamingAdapter {

    private final AddressGermanyInPort addressGermanyInPort;
    private final AddressMunichInPort addressMunichInPort;
    private final StreetsMunichInPort streetsMunichInPort;
    private final ProcessApi processApi;
    private final ErrorApi errorApi;

    private final AddressMapper addressServiceMapper;

    private final String RESPONSE = "response";

    public Consumer<Message<SearchAdressenDeutschlandDto>> searchAddressesGermany() {
        return message -> {
            try {
                log.debug(message.toString());
                final SearchAddressesGermanyModel model = this.addressServiceMapper.dto2Model(message.getPayload());
                final BundesweiteAdresseResponse result = this.addressGermanyInPort.searchAddresses(model);
                this.correlateProcessMessage(message.getHeaders(), Map.of(RESPONSE, result));
            } catch (final BpmnError bpmnError) {
                this.errorApi.handleBpmnError(message.getHeaders(), bpmnError);
            } catch (final IncidentError incidentError) {
                this.errorApi.handleIncident(message.getHeaders(), incidentError);
            }
        };
    }

    public Consumer<Message<CheckAdresseMuenchenDto>> checkAddressMunich() {
        return message -> {
            try {
                log.debug(message.toString());
                final CheckAddressesModel model = this.addressServiceMapper.dto2Model(message.getPayload());
                final MuenchenAdresse result = this.addressMunichInPort.checkAddress(model);
                this.correlateProcessMessage(message.getHeaders(), Map.of(RESPONSE, result));
            } catch (final BpmnError bpmnError) {
                this.errorApi.handleBpmnError(message.getHeaders(), bpmnError);
            } catch (final IncidentError incidentError) {
                this.errorApi.handleIncident(message.getHeaders(), incidentError);
            }
        };
    }

    public Consumer<Message<ListAdressenMuenchenDto>> listAddressesMunich() {
        return message -> {
            try {
                log.debug(message.toString());
                final ListAddressesModel model = this.addressServiceMapper.dto2Model(message.getPayload());
                final MuenchenAdresseResponse result = this.addressMunichInPort.listAddresses(model);
                this.correlateProcessMessage(message.getHeaders(), Map.of(RESPONSE, result));
            } catch (final BpmnError bpmnError) {
                this.errorApi.handleBpmnError(message.getHeaders(), bpmnError);
            } catch (final IncidentError incidentError) {
                this.errorApi.handleIncident(message.getHeaders(), incidentError);
            }
        };
    }

    public Consumer<Message<ListAenderungenMuenchenDto>> listChangesMunich() {
        return message -> {
            try {
                log.debug(message.toString());
                final ListAddressChangesModel model = this.addressServiceMapper.dto2Model(message.getPayload());
                final AenderungResponse result = this.addressMunichInPort.listChanges(model);
                this.correlateProcessMessage(message.getHeaders(), Map.of(RESPONSE, result));
            } catch (final BpmnError bpmnError) {
                this.errorApi.handleBpmnError(message.getHeaders(), bpmnError);
            } catch (final IncidentError incidentError) {
                this.errorApi.handleIncident(message.getHeaders(), incidentError);
            }
        };
    }

    public Consumer<Message<SearchAdressenMuenchenDto>> searchAddressesMunich() {
        return message -> {
            try {
                log.debug(message.toString());
                final SearchAddressesModel model = this.addressServiceMapper.dto2Model(message.getPayload());
                final MuenchenAdresseResponse result = this.addressMunichInPort.searchAddresses(model);
                this.correlateProcessMessage(message.getHeaders(), Map.of(RESPONSE, result));
            } catch (final BpmnError bpmnError) {
                this.errorApi.handleBpmnError(message.getHeaders(), bpmnError);
            } catch (final IncidentError incidentError) {
                this.errorApi.handleIncident(message.getHeaders(), incidentError);
            }
        };
    }

    public Consumer<Message<SearchAdressenGeoMuenchenDto>> searchAddressesGeoMunich() {
        return message -> {
            try {
                log.debug(message.toString());
                final SearchAddressesGeoModel model = this.addressServiceMapper.dto2Model(message.getPayload());
                final AddressDistancesModel result = this.addressMunichInPort.searchAddressesGeo(model);
                this.correlateProcessMessage(message.getHeaders(), Map.of(RESPONSE, result));
            } catch (final BpmnError bpmnError) {
                this.errorApi.handleBpmnError(message.getHeaders(), bpmnError);
            } catch (final IncidentError incidentError) {
                this.errorApi.handleIncident(message.getHeaders(), incidentError);
            }
        };
    }

    public Consumer<Message<StrassenIdDto>> findStreetByIdMunich() {
        return message -> {
            try {
                log.debug(message.toString());
                final long streetId = message.getPayload().getStrasseId();
                final Strasse result = this.streetsMunichInPort.findStreetsById(streetId);
                this.correlateProcessMessage(message.getHeaders(), Map.of(RESPONSE, result));
            } catch (final BpmnError bpmnError) {
                this.errorApi.handleBpmnError(message.getHeaders(), bpmnError);
            } catch (final IncidentError incidentError) {
                this.errorApi.handleIncident(message.getHeaders(), incidentError);
            }
        };
    }

    public Consumer<Message<ListStrassenDto>> listStreetMunich() {
        return message -> {
            try {
                log.debug(message.toString());
                final ListStreetsModel model = this.addressServiceMapper.dto2Model(message.getPayload());
                final StrasseResponse result = this.streetsMunichInPort.listStreets(model);
                this.correlateProcessMessage(message.getHeaders(), Map.of(RESPONSE, result));
            } catch (final BpmnError bpmnError) {
                this.errorApi.handleBpmnError(message.getHeaders(), bpmnError);
            } catch (final IncidentError incidentError) {
                this.errorApi.handleIncident(message.getHeaders(), incidentError);
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
