package integration.adapter.in.streaming;

import de.muenchen.oss.digiwf.address.integration.adapter.in.streaming.AddressMapperImpl;
import de.muenchen.oss.digiwf.address.integration.adapter.in.streaming.MessageProcessor;
import de.muenchen.oss.digiwf.address.integration.adapter.in.streaming.dto.*;
import de.muenchen.oss.digiwf.address.integration.application.port.in.AddressGermanyInPort;
import de.muenchen.oss.digiwf.address.integration.application.port.in.AddressMunichInPort;
import de.muenchen.oss.digiwf.address.integration.application.port.in.StreetsMunichInPort;
import de.muenchen.oss.digiwf.address.integration.application.port.out.IntegrationOutPort;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.*;
import de.muenchen.oss.digiwf.address.integration.client.model.response.AddressDistancesModel;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.Map;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_MESSAGE_NAME;
import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_PROCESS_INSTANCE_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MessageProcessorTest {

    private final AddressGermanyInPort addressGermanyInPort = Mockito.mock(AddressGermanyInPort.class);
    private final AddressMunichInPort addressMunichInPort = Mockito.mock(AddressMunichInPort.class);
    private final StreetsMunichInPort streetsMunichInPort = Mockito.mock(StreetsMunichInPort.class);
    private final IntegrationOutPort integrationOutPort = Mockito.mock(IntegrationOutPort.class);


    private MessageProcessor messageProcessor;



    @BeforeEach
    void setup() {
        messageProcessor = new MessageProcessor(addressGermanyInPort, addressMunichInPort, streetsMunichInPort, integrationOutPort, new AddressMapperImpl());
    }

    @Test
    void testSearchAddressesGermany() {
        final Message<SearchAdressenDeutschlandDto> searchAdressenDeutschlandMsg = createMessageHelper(SearchAdressenDeutschlandDto.builder().build());
        when(addressGermanyInPort.searchAddresses(any())).thenReturn(new BundesweiteAdresseResponse());
        messageProcessor.searchAddressesGermany().accept(searchAdressenDeutschlandMsg);
        verify(integrationOutPort, times(1)).correlateProcessMessage(any(), anyMap());
    }

    @Test
    void testSearchAddressesGermanyBpmnErrorHandling() {
        final Message<SearchAdressenDeutschlandDto> searchAdressenDeutschlandMsg = createMessageHelper(SearchAdressenDeutschlandDto.builder().build());
        when(addressGermanyInPort.searchAddresses(any())).thenThrow(new BpmnError("400", "BpmnError"));
        messageProcessor.searchAddressesGermany().accept(searchAdressenDeutschlandMsg);
        verify(integrationOutPort, times(1)).handleBpmnError(any(), any());
    }

    @Test
    void testSearchAddressesGermanyIncidentErrorHandling() {
        final Message<SearchAdressenDeutschlandDto> searchAdressenDeutschlandMsg = createMessageHelper(SearchAdressenDeutschlandDto.builder().build());
        when(addressGermanyInPort.searchAddresses(any())).thenThrow(new IncidentError("IncidentError"));
        messageProcessor.searchAddressesGermany().accept(searchAdressenDeutschlandMsg);
        verify(integrationOutPort, times(1)).handleIncident(any(), any());
    }

    @Test
    void testCheckAddressMunich() {
        final Message<CheckAdresseMuenchenDto> checkAdresseMuenchenMsg = createMessageHelper(new CheckAdresseMuenchenDto());
        when(addressMunichInPort.checkAddress(any())).thenReturn(new MuenchenAdresse());
        messageProcessor.checkAddressMunich().accept(checkAdresseMuenchenMsg);
        verify(integrationOutPort, times(1)).correlateProcessMessage(any(), anyMap());
    }

    @Test
    void testCheckAddressMunichBpmnErrorHandling() {
        final Message<CheckAdresseMuenchenDto> checkAdresseMuenchenMsg = createMessageHelper(new CheckAdresseMuenchenDto());
        when(addressMunichInPort.checkAddress(any())).thenThrow(new BpmnError("400", "BpmnError"));
        messageProcessor.checkAddressMunich().accept(checkAdresseMuenchenMsg);
        verify(integrationOutPort, times(1)).handleBpmnError(any(), any());
    }

    @Test
    void testCheckAddressMunichIncidentErrorHandling() {
        final Message<CheckAdresseMuenchenDto> checkAdresseMuenchenMsg = createMessageHelper(new CheckAdresseMuenchenDto());
        when(addressMunichInPort.checkAddress(any())).thenThrow(new IncidentError("IncidentError"));
        messageProcessor.checkAddressMunich().accept(checkAdresseMuenchenMsg);
        verify(integrationOutPort, times(1)).handleIncident(any(), any());
    }

    @Test
    void testListAddressesMunich() {
        final Message<ListAdressenMuenchenDto> listAdresseMuenchenMsg = createMessageHelper(new ListAdressenMuenchenDto());
        when(addressMunichInPort.listAddresses(any())).thenReturn(new MuenchenAdresseResponse());
        messageProcessor.listAddressesMunich().accept(listAdresseMuenchenMsg);
        verify(integrationOutPort, times(1)).correlateProcessMessage(any(), anyMap());
    }

    @Test
    void testListAddressesMunichBpmnErrorHandling() {
        final Message<ListAdressenMuenchenDto> listAdresseMuenchenMsg = createMessageHelper(new ListAdressenMuenchenDto());
        when(addressMunichInPort.listAddresses(any())).thenThrow(new BpmnError("400", "BpmnError"));
        messageProcessor.listAddressesMunich().accept(listAdresseMuenchenMsg);
        verify(integrationOutPort, times(1)).handleBpmnError(any(), any());
    }

    @Test
    void testListAddressesMunichIncidentErrorHandling() {
        final Message<ListAdressenMuenchenDto> listAdresseMuenchenMsg = createMessageHelper(new ListAdressenMuenchenDto());
        when(addressMunichInPort.listAddresses(any())).thenThrow(new IncidentError("IncidentError"));
        messageProcessor.listAddressesMunich().accept(listAdresseMuenchenMsg);
        verify(integrationOutPort, times(1)).handleIncident(any(), any());
    }

    @Test
    void testListChangesMunich() {
        final Message<ListAenderungenMuenchenDto> listAdresseMuenchenMsg = createMessageHelper(new ListAenderungenMuenchenDto());
        when(addressMunichInPort.listChanges(any())).thenReturn(new AenderungResponse());
        messageProcessor.listChangesMunich().accept(listAdresseMuenchenMsg);
        verify(integrationOutPort, times(1)).correlateProcessMessage(any(), anyMap());
    }

    @Test
    void testListChangesMunichBpmnErrorHandling() {
        final Message<ListAenderungenMuenchenDto> listAdresseMuenchenMsg = createMessageHelper(new ListAenderungenMuenchenDto());
        when(addressMunichInPort.listChanges(any())).thenThrow(new BpmnError("400", "BpmnError"));
        messageProcessor.listChangesMunich().accept(listAdresseMuenchenMsg);
        verify(integrationOutPort, times(1)).handleBpmnError(any(), any());
    }

    @Test
    void testListChangesMunichIncidentErrorHandling() {
        final Message<ListAenderungenMuenchenDto> listAdresseMuenchenMsg = createMessageHelper(new ListAenderungenMuenchenDto());
        when(addressMunichInPort.listChanges(any())).thenThrow(new IncidentError("IncidentError"));
        messageProcessor.listChangesMunich().accept(listAdresseMuenchenMsg);
        verify(integrationOutPort, times(1)).handleIncident(any(), any());
    }

    @Test
    void testSearchAddressesMunich() {
        final Message<SearchAdressenMuenchenDto> searchAdressenMuenchenMsg = createMessageHelper(new SearchAdressenMuenchenDto());
        when(addressMunichInPort.searchAddresses(any())).thenReturn(new MuenchenAdresseResponse());
        messageProcessor.searchAddressesMunich().accept(searchAdressenMuenchenMsg);
        verify(integrationOutPort, times(1)).correlateProcessMessage(any(), anyMap());
    }

    @Test
    void testSearchAddressesMunichBpmnErrorHandling() {
        final Message<SearchAdressenMuenchenDto> searchAdressenMuenchenMsg = createMessageHelper(new SearchAdressenMuenchenDto());
        when(addressMunichInPort.searchAddresses(any())).thenThrow(new BpmnError("400", "BpmnError"));
        messageProcessor.searchAddressesMunich().accept(searchAdressenMuenchenMsg);
        verify(integrationOutPort, times(1)).handleBpmnError(any(), any());
    }

    @Test
    void testSearchAddressesMunichIncidentErrorHandling() {
        final Message<SearchAdressenMuenchenDto> searchAdressenMuenchenMsg = createMessageHelper(new SearchAdressenMuenchenDto());
        when(addressMunichInPort.searchAddresses(any())).thenThrow(new IncidentError("IncidentError"));
        messageProcessor.searchAddressesMunich().accept(searchAdressenMuenchenMsg);
        verify(integrationOutPort, times(1)).handleIncident(any(), any());
    }

    @Test
    void testSearchAddressesGeoMunich() {
        final Message<SearchAdressenGeoMuenchenDto> searchAdressenGeoMuenchenMsg = createMessageHelper(new SearchAdressenGeoMuenchenDto());
        when(addressMunichInPort.searchAddressesGeo(any())).thenReturn(AddressDistancesModel.builder().build());
        messageProcessor.searchAddressesGeoMunich().accept(searchAdressenGeoMuenchenMsg);
        verify(integrationOutPort, times(1)).correlateProcessMessage(any(), anyMap());
    }

    @Test
    void testSearchAddressesGeoMunichBpmnErrorHandling() {
        final Message<SearchAdressenGeoMuenchenDto> searchAdressenGeoMuenchenMsg = createMessageHelper(new SearchAdressenGeoMuenchenDto());
        when(addressMunichInPort.searchAddressesGeo(any())).thenThrow(new BpmnError("400", "BpmnError"));
        messageProcessor.searchAddressesGeoMunich().accept(searchAdressenGeoMuenchenMsg);
        verify(integrationOutPort, times(1)).handleBpmnError(any(), any());
    }

    @Test
    void testSearchAddressesGeoMunichIncidentErrorHandling() {
        final Message<SearchAdressenGeoMuenchenDto> searchAdressenGeoMuenchenMsg = createMessageHelper(new SearchAdressenGeoMuenchenDto());
        when(addressMunichInPort.searchAddressesGeo(any())).thenThrow(new IncidentError("IncidentError"));
        messageProcessor.searchAddressesGeoMunich().accept(searchAdressenGeoMuenchenMsg);
        verify(integrationOutPort, times(1)).handleIncident(any(), any());
    }

    @Test
    void testFindStreetByIdMunich() {
        final StrassenIdDto strassenIdDto = new StrassenIdDto();
        strassenIdDto.setStrasseId(1L);
        final Message<StrassenIdDto> msg = createMessageHelper(strassenIdDto);
        when(streetsMunichInPort.findStreetsById(anyLong())).thenReturn(new Strasse());
        messageProcessor.findStreetByIdMunich().accept(msg);
        verify(integrationOutPort, times(1)).correlateProcessMessage(any(), anyMap());
    }

    @Test
    void testFindStreetByIdMunichBpmnErrorHandling() {
        final StrassenIdDto strassenIdDto = new StrassenIdDto();
        strassenIdDto.setStrasseId(1L);
        final Message<StrassenIdDto> msg = createMessageHelper(strassenIdDto);
        when(streetsMunichInPort.findStreetsById(anyLong())).thenThrow(new BpmnError("400", "BpmnError"));
        messageProcessor.findStreetByIdMunich().accept(msg);
        verify(integrationOutPort, times(1)).handleBpmnError(any(), any());
    }

    @Test
    void testFindStreetByIdMunichIncidentErrorHandling() {
        final StrassenIdDto strassenIdDto = new StrassenIdDto();
        strassenIdDto.setStrasseId(1L);
        final Message<StrassenIdDto> msg = createMessageHelper(strassenIdDto);
        when(streetsMunichInPort.findStreetsById(anyLong())).thenThrow(new IncidentError("IncidentError"));
        messageProcessor.findStreetByIdMunich().accept(msg);
        verify(integrationOutPort, times(1)).handleIncident(any(), any());
    }

    @Test
    void testListStreetsMunich() {
        final Message<ListStrassenDto> listStrassenMsg = createMessageHelper(new ListStrassenDto());
        when(streetsMunichInPort.listStreets(any())).thenReturn(new StrasseResponse());
        messageProcessor.listStreetMunich().accept(listStrassenMsg);
        verify(integrationOutPort, times(1)).correlateProcessMessage(any(), anyMap());
    }

    @Test
    void testListStreetsMunichBpmnErrorHandling() {
        final Message<ListStrassenDto> listStrassenMsg = createMessageHelper(new ListStrassenDto());
        when(streetsMunichInPort.listStreets(any())).thenThrow(new BpmnError("400", "BpmnError"));
        messageProcessor.listStreetMunich().accept(listStrassenMsg);
        verify(integrationOutPort, times(1)).handleBpmnError(any(), any());
    }

    @Test
    void testListStreetsMunichIncidentErrorHandling() {
        final Message<ListStrassenDto> listStrassenMsg = createMessageHelper(new ListStrassenDto());
        when(streetsMunichInPort.listStreets(any())).thenThrow(new IncidentError("IncidentError"));
        messageProcessor.listStreetMunich().accept(listStrassenMsg);
        verify(integrationOutPort, times(1)).handleIncident(any(), any());
    }

    private <T> Message<T> createMessageHelper(final T payload) {
        return new Message<>() {
            @Override
            public T getPayload() {
                return payload;
            }

            @Override
            public MessageHeaders getHeaders() {
                return new MessageHeaders(Map.of(DIGIWF_PROCESS_INSTANCE_ID, "exampleProcessInstanceId", DIGIWF_MESSAGE_NAME, "messageName"));
            }
        };
    }

}
