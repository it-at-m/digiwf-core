package de.muenchen.oss.digiwf.address.integration.adapter.in.streaming;

import de.muenchen.oss.digiwf.address.integration.adapter.in.streaming.dto.*;
import de.muenchen.oss.digiwf.address.integration.application.port.in.AddressGermanyInPort;
import de.muenchen.oss.digiwf.address.integration.application.port.in.AddressMunichInPort;
import de.muenchen.oss.digiwf.address.integration.application.port.in.StreetsMunichInPort;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.*;
import de.muenchen.oss.digiwf.address.integration.client.model.response.AddressDistancesModel;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.Map;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_INTEGRATION_NAME;
import static de.muenchen.oss.digiwf.message.common.MessageConstants.DIGIWF_PROCESS_INSTANCE_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StreamingAdapterTest {

    private final AddressGermanyInPort addressGermanyInPort = Mockito.mock(AddressGermanyInPort.class);
    private final AddressMunichInPort addressMunichInPort = Mockito.mock(AddressMunichInPort.class);
    private final StreetsMunichInPort streetsMunichInPort = Mockito.mock(StreetsMunichInPort.class);
    private final ProcessApi processApi = mock(ProcessApi.class);
    private final ErrorApi errorApi = mock(ErrorApi.class);


    private StreamingAdapter streamingAdapter;


    @BeforeEach
    void setup() {
        streamingAdapter = new StreamingAdapter(addressGermanyInPort, addressMunichInPort, streetsMunichInPort, processApi, errorApi, new AddressMapperImpl());
    }

    @Test
    void testSearchAddressesGermany() {
        final Message<SearchAdressenDeutschlandDto> searchAdressenDeutschlandMsg = createMessageHelper(SearchAdressenDeutschlandDto.builder().build());
        when(addressGermanyInPort.searchAddresses(any())).thenReturn(new BundesweiteAdresseResponse());
        streamingAdapter.searchAddressesGermany().accept(searchAdressenDeutschlandMsg);
        verify(processApi, times(1)).correlateMessage(anyString(), anyString(), anyString(), anyMap());
    }

    @Test
    void testSearchAddressesGermanyBpmnErrorHandling() {
        final Message<SearchAdressenDeutschlandDto> searchAdressenDeutschlandMsg = createMessageHelper(SearchAdressenDeutschlandDto.builder().build());
        when(addressGermanyInPort.searchAddresses(any())).thenThrow(new BpmnError("400", "BpmnError"));
        streamingAdapter.searchAddressesGermany().accept(searchAdressenDeutschlandMsg);
        verify(errorApi, times(1)).handleBpmnError(any(), any());
    }

    @Test
    void testSearchAddressesGermanyIncidentErrorHandling() {
        final Message<SearchAdressenDeutschlandDto> searchAdressenDeutschlandMsg = createMessageHelper(SearchAdressenDeutschlandDto.builder().build());
        when(addressGermanyInPort.searchAddresses(any())).thenThrow(new IncidentError("IncidentError"));
        streamingAdapter.searchAddressesGermany().accept(searchAdressenDeutschlandMsg);
        verify(errorApi, times(1)).handleIncident(any(), (IncidentError) any());
    }

    @Test
    void testCheckAddressMunich() {
        final Message<CheckAdresseMuenchenDto> checkAdresseMuenchenMsg = createMessageHelper(new CheckAdresseMuenchenDto());
        when(addressMunichInPort.checkAddress(any())).thenReturn(new MuenchenAdresse());
        streamingAdapter.checkAddressMunich().accept(checkAdresseMuenchenMsg);
        verify(processApi, times(1)).correlateMessage(anyString(), anyString(), anyString(), anyMap());
    }

    @Test
    void testCheckAddressMunichBpmnErrorHandling() {
        final Message<CheckAdresseMuenchenDto> checkAdresseMuenchenMsg = createMessageHelper(new CheckAdresseMuenchenDto());
        when(addressMunichInPort.checkAddress(any())).thenThrow(new BpmnError("400", "BpmnError"));
        streamingAdapter.checkAddressMunich().accept(checkAdresseMuenchenMsg);
        verify(errorApi, times(1)).handleBpmnError(any(), any());
    }

    @Test
    void testCheckAddressMunichIncidentErrorHandling() {
        final Message<CheckAdresseMuenchenDto> checkAdresseMuenchenMsg = createMessageHelper(new CheckAdresseMuenchenDto());
        when(addressMunichInPort.checkAddress(any())).thenThrow(new IncidentError("IncidentError"));
        streamingAdapter.checkAddressMunich().accept(checkAdresseMuenchenMsg);
        verify(errorApi, times(1)).handleIncident(any(), (IncidentError) any());
    }

    @Test
    void testListAddressesMunich() {
        final Message<ListAdressenMuenchenDto> listAdresseMuenchenMsg = createMessageHelper(new ListAdressenMuenchenDto());
        when(addressMunichInPort.listAddresses(any())).thenReturn(new MuenchenAdresseResponse());
        streamingAdapter.listAddressesMunich().accept(listAdresseMuenchenMsg);
        verify(processApi, times(1)).correlateMessage(anyString(), anyString(), anyString(), anyMap());
    }

    @Test
    void testListAddressesMunichBpmnErrorHandling() {
        final Message<ListAdressenMuenchenDto> listAdresseMuenchenMsg = createMessageHelper(new ListAdressenMuenchenDto());
        when(addressMunichInPort.listAddresses(any())).thenThrow(new BpmnError("400", "BpmnError"));
        streamingAdapter.listAddressesMunich().accept(listAdresseMuenchenMsg);
        verify(errorApi, times(1)).handleBpmnError(any(), any());
    }

    @Test
    void testListAddressesMunichIncidentErrorHandling() {
        final Message<ListAdressenMuenchenDto> listAdresseMuenchenMsg = createMessageHelper(new ListAdressenMuenchenDto());
        when(addressMunichInPort.listAddresses(any())).thenThrow(new IncidentError("IncidentError"));
        streamingAdapter.listAddressesMunich().accept(listAdresseMuenchenMsg);
        verify(errorApi, times(1)).handleIncident(any(), (IncidentError) any());
    }

    @Test
    void testListChangesMunich() {
        final Message<ListAenderungenMuenchenDto> listAdresseMuenchenMsg = createMessageHelper(new ListAenderungenMuenchenDto());
        when(addressMunichInPort.listChanges(any())).thenReturn(new AenderungResponse());
        streamingAdapter.listChangesMunich().accept(listAdresseMuenchenMsg);
        verify(processApi, times(1)).correlateMessage(anyString(), anyString(), anyString(), anyMap());
    }

    @Test
    void testListChangesMunichBpmnErrorHandling() {
        final Message<ListAenderungenMuenchenDto> listAdresseMuenchenMsg = createMessageHelper(new ListAenderungenMuenchenDto());
        when(addressMunichInPort.listChanges(any())).thenThrow(new BpmnError("400", "BpmnError"));
        streamingAdapter.listChangesMunich().accept(listAdresseMuenchenMsg);
        verify(errorApi, times(1)).handleBpmnError(any(), any());
    }

    @Test
    void testListChangesMunichIncidentErrorHandling() {
        final Message<ListAenderungenMuenchenDto> listAdresseMuenchenMsg = createMessageHelper(new ListAenderungenMuenchenDto());
        when(addressMunichInPort.listChanges(any())).thenThrow(new IncidentError("IncidentError"));
        streamingAdapter.listChangesMunich().accept(listAdresseMuenchenMsg);
        verify(errorApi, times(1)).handleIncident(any(), (IncidentError) any());
    }

    @Test
    void testSearchAddressesMunich() {
        final Message<SearchAdressenMuenchenDto> searchAdressenMuenchenMsg = createMessageHelper(new SearchAdressenMuenchenDto());
        when(addressMunichInPort.searchAddresses(any())).thenReturn(new MuenchenAdresseResponse());
        streamingAdapter.searchAddressesMunich().accept(searchAdressenMuenchenMsg);
        verify(processApi, times(1)).correlateMessage(anyString(), anyString(), anyString(), anyMap());
    }

    @Test
    void testSearchAddressesMunichBpmnErrorHandling() {
        final Message<SearchAdressenMuenchenDto> searchAdressenMuenchenMsg = createMessageHelper(new SearchAdressenMuenchenDto());
        when(addressMunichInPort.searchAddresses(any())).thenThrow(new BpmnError("400", "BpmnError"));
        streamingAdapter.searchAddressesMunich().accept(searchAdressenMuenchenMsg);
        verify(errorApi, times(1)).handleBpmnError(any(), any());
    }

    @Test
    void testSearchAddressesMunichIncidentErrorHandling() {
        final Message<SearchAdressenMuenchenDto> searchAdressenMuenchenMsg = createMessageHelper(new SearchAdressenMuenchenDto());
        when(addressMunichInPort.searchAddresses(any())).thenThrow(new IncidentError("IncidentError"));
        streamingAdapter.searchAddressesMunich().accept(searchAdressenMuenchenMsg);
        verify(errorApi, times(1)).handleIncident(any(), (IncidentError) any());
    }

    @Test
    void testSearchAddressesGeoMunich() {
        final Message<SearchAdressenGeoMuenchenDto> searchAdressenGeoMuenchenMsg = createMessageHelper(new SearchAdressenGeoMuenchenDto());
        when(addressMunichInPort.searchAddressesGeo(any())).thenReturn(AddressDistancesModel.builder().build());
        streamingAdapter.searchAddressesGeoMunich().accept(searchAdressenGeoMuenchenMsg);
        verify(processApi, times(1)).correlateMessage(anyString(), anyString(), anyString(), anyMap());
    }

    @Test
    void testSearchAddressesGeoMunichBpmnErrorHandling() {
        final Message<SearchAdressenGeoMuenchenDto> searchAdressenGeoMuenchenMsg = createMessageHelper(new SearchAdressenGeoMuenchenDto());
        when(addressMunichInPort.searchAddressesGeo(any())).thenThrow(new BpmnError("400", "BpmnError"));
        streamingAdapter.searchAddressesGeoMunich().accept(searchAdressenGeoMuenchenMsg);
        verify(errorApi, times(1)).handleBpmnError(any(), any());
    }

    @Test
    void testSearchAddressesGeoMunichIncidentErrorHandling() {
        final Message<SearchAdressenGeoMuenchenDto> searchAdressenGeoMuenchenMsg = createMessageHelper(new SearchAdressenGeoMuenchenDto());
        when(addressMunichInPort.searchAddressesGeo(any())).thenThrow(new IncidentError("IncidentError"));
        streamingAdapter.searchAddressesGeoMunich().accept(searchAdressenGeoMuenchenMsg);
        verify(errorApi, times(1)).handleIncident(any(), (IncidentError) any());
    }

    @Test
    void testFindStreetByIdMunich() {
        final StrassenIdDto strassenIdDto = new StrassenIdDto();
        strassenIdDto.setStrasseId(1L);
        final Message<StrassenIdDto> msg = createMessageHelper(strassenIdDto);
        when(streetsMunichInPort.findStreetsById(anyLong())).thenReturn(new Strasse());
        streamingAdapter.findStreetByIdMunich().accept(msg);
        verify(processApi, times(1)).correlateMessage(anyString(), anyString(), anyString(), anyMap());
    }

    @Test
    void testFindStreetByIdMunichBpmnErrorHandling() {
        final StrassenIdDto strassenIdDto = new StrassenIdDto();
        strassenIdDto.setStrasseId(1L);
        final Message<StrassenIdDto> msg = createMessageHelper(strassenIdDto);
        when(streetsMunichInPort.findStreetsById(anyLong())).thenThrow(new BpmnError("400", "BpmnError"));
        streamingAdapter.findStreetByIdMunich().accept(msg);
        verify(errorApi, times(1)).handleBpmnError(any(), any());
    }

    @Test
    void testFindStreetByIdMunichIncidentErrorHandling() {
        final StrassenIdDto strassenIdDto = new StrassenIdDto();
        strassenIdDto.setStrasseId(1L);
        final Message<StrassenIdDto> msg = createMessageHelper(strassenIdDto);
        when(streetsMunichInPort.findStreetsById(anyLong())).thenThrow(new IncidentError("IncidentError"));
        streamingAdapter.findStreetByIdMunich().accept(msg);
        verify(errorApi, times(1)).handleIncident(any(), (IncidentError) any());
    }

    @Test
    void testListStreetsMunich() {
        final Message<ListStrassenDto> listStrassenMsg = createMessageHelper(new ListStrassenDto());
        when(streetsMunichInPort.listStreets(any())).thenReturn(new StrasseResponse());
        streamingAdapter.listStreetMunich().accept(listStrassenMsg);
        verify(processApi, times(1)).correlateMessage(anyString(), anyString(), anyString(), anyMap());
    }

    @Test
    void testListStreetsMunichBpmnErrorHandling() {
        final Message<ListStrassenDto> listStrassenMsg = createMessageHelper(new ListStrassenDto());
        when(streetsMunichInPort.listStreets(any())).thenThrow(new BpmnError("400", "BpmnError"));
        streamingAdapter.listStreetMunich().accept(listStrassenMsg);
        verify(errorApi, times(1)).handleBpmnError(any(), any());
    }

    @Test
    void testListStreetsMunichIncidentErrorHandling() {
        final Message<ListStrassenDto> listStrassenMsg = createMessageHelper(new ListStrassenDto());
        when(streetsMunichInPort.listStreets(any())).thenThrow(new IncidentError("IncidentError"));
        streamingAdapter.listStreetMunich().accept(listStrassenMsg);
        verify(errorApi, times(1)).handleIncident(any(), (IncidentError) any());
    }

    private <T> Message<T> createMessageHelper(final T payload) {
        return new Message<>() {
            @Override
            public T getPayload() {
                return payload;
            }

            @Override
            public MessageHeaders getHeaders() {
                return new MessageHeaders(Map.of(DIGIWF_PROCESS_INSTANCE_ID, "exampleProcessInstanceId", DIGIWF_INTEGRATION_NAME, "addressIntegration", "type", "addressType"));
            }
        };
    }

}
