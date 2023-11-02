package de.muenchen.oss.digiwf.address.integration.adapter.in.streaming;

import de.muenchen.oss.digiwf.address.integration.adapter.in.streaming.dto.*;
import de.muenchen.oss.digiwf.address.integration.application.port.in.AddressGermanyInPort;
import de.muenchen.oss.digiwf.address.integration.application.port.in.AddressMunichInPort;
import de.muenchen.oss.digiwf.address.integration.application.port.in.StreetsMunichInPort;
import de.muenchen.oss.digiwf.address.integration.application.port.out.IntegrationOutPort;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.*;
import de.muenchen.oss.digiwf.address.integration.client.model.request.*;
import de.muenchen.oss.digiwf.address.integration.client.model.response.AddressDistancesModel;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageProcessor {

    private final AddressGermanyInPort addressGermanyInPort;
    private final AddressMunichInPort addressMunichInPort;
    private final StreetsMunichInPort streetsMunichInPort;
    private final IntegrationOutPort integrationOutPort;

    private final AddressMapper addressServiceMapper;

    private final String RESPONSE = "response";

    @Bean
    public Consumer<Message<SearchAdressenDeutschlandDto>> searchAddressesGermany() {
        return message -> {
            try {
                log.debug(message.toString());
                final SearchAddressesGermanyModel model = this.addressServiceMapper.dto2Model(message.getPayload());
                final BundesweiteAdresseResponse result = this.addressGermanyInPort.searchAddresses(model);
                this.integrationOutPort.correlateProcessMessage(message.getHeaders(), Map.of(RESPONSE, result));
            } catch (final BpmnError bpmnError) {
                this.integrationOutPort.handleBpmnError(message.getHeaders(), bpmnError);
            } catch (final IncidentError incidentError) {
                this.integrationOutPort.handleIncident(message.getHeaders(), incidentError);
            }
        };
    }

    @Bean
    public Consumer<Message<CheckAdresseMuenchenDto>> checkAddressMunich() {
        return message -> {
            try {
                log.debug(message.toString());
                final CheckAddressesModel model = this.addressServiceMapper.dto2Model(message.getPayload());
                final MuenchenAdresse result = this.addressMunichInPort.checkAddress(model);
                this.integrationOutPort.correlateProcessMessage(message.getHeaders(), Map.of(RESPONSE, result));
            } catch (final BpmnError bpmnError) {
                this.integrationOutPort.handleBpmnError(message.getHeaders(), bpmnError);
            } catch (final IncidentError incidentError) {
                this.integrationOutPort.handleIncident(message.getHeaders(), incidentError);
            }
        };
    }

    @Bean
    public Consumer<Message<ListAdressenMuenchenDto>> listAddressesMunich() {
        return message -> {
            try {
                log.debug(message.toString());
                final ListAddressesModel model = this.addressServiceMapper.dto2Model(message.getPayload());
                final MuenchenAdresseResponse result = this.addressMunichInPort.listAddresses(model);
                this.integrationOutPort.correlateProcessMessage(message.getHeaders(), Map.of(RESPONSE, result));
            } catch (final BpmnError bpmnError) {
                this.integrationOutPort.handleBpmnError(message.getHeaders(), bpmnError);
            } catch (final IncidentError incidentError) {
                this.integrationOutPort.handleIncident(message.getHeaders(), incidentError);
            }
        };
    }

    @Bean
    public Consumer<Message<ListAenderungenMuenchenDto>> listChangesMunich() {
        return message -> {
            try {
                log.debug(message.toString());
                final ListAddressChangesModel model = this.addressServiceMapper.dto2Model(message.getPayload());
                final AenderungResponse result = this.addressMunichInPort.listChanges(model);
                this.integrationOutPort.correlateProcessMessage(message.getHeaders(), Map.of(RESPONSE, result));
            } catch (final BpmnError bpmnError) {
                this.integrationOutPort.handleBpmnError(message.getHeaders(), bpmnError);
            } catch (final IncidentError incidentError) {
                this.integrationOutPort.handleIncident(message.getHeaders(), incidentError);
            }
        };
    }

    @Bean
    public Consumer<Message<SearchAdressenMuenchenDto>> searchAddressesMunich() {
        return message -> {
            try {
                log.debug(message.toString());
                final SearchAddressesModel model = this.addressServiceMapper.dto2Model(message.getPayload());
                final MuenchenAdresseResponse result = this.addressMunichInPort.searchAddresses(model);
                this.integrationOutPort.correlateProcessMessage(message.getHeaders(), Map.of(RESPONSE, result));
            } catch (final BpmnError bpmnError) {
                this.integrationOutPort.handleBpmnError(message.getHeaders(), bpmnError);
            } catch (final IncidentError incidentError) {
                this.integrationOutPort.handleIncident(message.getHeaders(), incidentError);
            }
        };
    }

    @Bean
    public Consumer<Message<SearchAdressenGeoMuenchenDto>> searchAddressesGeoMunich() {
        return message -> {
            try {
                log.debug(message.toString());
                final SearchAddressesGeoModel model = this.addressServiceMapper.dto2Model(message.getPayload());
                final AddressDistancesModel result = this.addressMunichInPort.searchAddressesGeo(model);
                this.integrationOutPort.correlateProcessMessage(message.getHeaders(), Map.of(RESPONSE, result));
            } catch (final BpmnError bpmnError) {
                this.integrationOutPort.handleBpmnError(message.getHeaders(), bpmnError);
            } catch (final IncidentError incidentError) {
                this.integrationOutPort.handleIncident(message.getHeaders(), incidentError);
            }
        };
    }

    @Bean
    public Consumer<Message<StrassenIdDto>> findStreetByIdMunich() {
        return message -> {
            try {
                log.debug(message.toString());
                final long streetId = message.getPayload().getStrasseId();
                final Strasse result = this.streetsMunichInPort.findStreetsById(streetId);
                this.integrationOutPort.correlateProcessMessage(message.getHeaders(), Map.of(RESPONSE, result));
            } catch (final BpmnError bpmnError) {
                this.integrationOutPort.handleBpmnError(message.getHeaders(), bpmnError);
            } catch (final IncidentError incidentError) {
                this.integrationOutPort.handleIncident(message.getHeaders(), incidentError);
            }
        };
    }

    @Bean
    public Consumer<Message<ListStrassenDto>> listStreetMunich() {
        return message -> {
            try {
                log.debug(message.toString());
                final ListStreetsModel model = this.addressServiceMapper.dto2Model(message.getPayload());
                final StrasseResponse result = this.streetsMunichInPort.listStreets(model);
                this.integrationOutPort.correlateProcessMessage(message.getHeaders(), Map.of(RESPONSE, result));
            } catch (final BpmnError bpmnError) {
                this.integrationOutPort.handleBpmnError(message.getHeaders(), bpmnError);
            } catch (final IncidentError incidentError) {
                this.integrationOutPort.handleIncident(message.getHeaders(), incidentError);
            }
        };
    }

}
