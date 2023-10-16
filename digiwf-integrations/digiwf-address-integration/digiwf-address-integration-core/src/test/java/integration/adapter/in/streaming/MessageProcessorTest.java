package integration.adapter.in.streaming;

import de.muenchen.oss.digiwf.address.integration.adapter.in.streaming.AddressMapperImpl;
import de.muenchen.oss.digiwf.address.integration.adapter.in.streaming.MessageProcessor;
import de.muenchen.oss.digiwf.address.integration.adapter.in.streaming.dto.*;
import de.muenchen.oss.digiwf.address.integration.application.port.in.AddressGermanyInPort;
import de.muenchen.oss.digiwf.address.integration.application.port.in.AddressMunichInPort;
import de.muenchen.oss.digiwf.address.integration.application.port.in.StreetsMunichInPort;
import de.muenchen.oss.digiwf.address.integration.application.port.out.IntegrationOutPort;
import de.muenchen.oss.digiwf.address.integration.gen.model.*;
import de.muenchen.oss.digiwf.address.integration.model.response.AddressDistancesModel;
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
        final Message<SearchAdressenDeutschlandDto> searchAdressenDeutschlandMsg = createMessageHelper(new SearchAdressenDeutschlandDto());
        when(addressGermanyInPort.searchAddresses(any())).thenReturn(new BundesweiteAdresseResponse());

        messageProcessor.searchAddressesGermany().accept(searchAdressenDeutschlandMsg);

        verify(addressGermanyInPort, times(1)).searchAddresses(any());
    }

    @Test
    void testCheckAddressMunich() {
        final Message<CheckAdresseMuenchenDto> checkAdresseMuenchenMsg = createMessageHelper(new CheckAdresseMuenchenDto());
        when(addressMunichInPort.checkAddress(any())).thenReturn(new MuenchenAdresse());

        messageProcessor.checkAddressMunich().accept(checkAdresseMuenchenMsg);

        verify(addressMunichInPort, times(1)).checkAddress(any());
    }

    @Test
    void testListAddressesMunich() {
        final Message<ListAdressenMuenchenDto> listAdresseMuenchenMsg = createMessageHelper(new ListAdressenMuenchenDto());
        when(addressMunichInPort.listAddresses(any())).thenReturn(new MuenchenAdresseResponse());

        messageProcessor.listAddressesMunich().accept(listAdresseMuenchenMsg);

        verify(addressMunichInPort, times(1)).listAddresses(any());
    }

    @Test
    void testListChangesMunich() {
        final Message<ListAenderungenMuenchenDto> listAdresseMuenchenMsg = createMessageHelper(new ListAenderungenMuenchenDto());
        when(addressMunichInPort.listChanges(any())).thenReturn(new AenderungResponse());

        messageProcessor.listChangesMunich().accept(listAdresseMuenchenMsg);

        verify(addressMunichInPort, times(1)).listChanges(any());
    }

    @Test
    void testSearchAddressesMunich() {
        final Message<SearchAdressenMuenchenDto> searchAdressenMuenchenMsg = createMessageHelper(new SearchAdressenMuenchenDto());
        when(addressMunichInPort.searchAddresses(any())).thenReturn(new MuenchenAdresseResponse());

        messageProcessor.searchAddressesMunich().accept(searchAdressenMuenchenMsg);

        verify(addressMunichInPort, times(1)).searchAddresses(any());
    }

    @Test
    void testSearchAddressesGeoMunich() {
        final Message<SearchAdressenGeoMuenchenDto> searchAdressenGeoMuenchenMsg = createMessageHelper(new SearchAdressenGeoMuenchenDto());
        when(addressMunichInPort.searchAddressesGeo(any())).thenReturn(AddressDistancesModel.builder().build());

        messageProcessor.searchAddressesGeoMunich().accept(searchAdressenGeoMuenchenMsg);

        verify(addressMunichInPort, times(1)).searchAddressesGeo(any());
    }

    @Test
    void testFindStreetByIdMunich() {
        final StrassenIdDto strassenIdDto = new StrassenIdDto();
        strassenIdDto.setStrasseId(1L);
        final Message<StrassenIdDto> streetsDto = createMessageHelper(strassenIdDto);
        when(streetsMunichInPort.findStreetsById(any())).thenReturn(new Strasse());

        messageProcessor.findStreetByIdMunich().accept(streetsDto);

        verify(streetsMunichInPort, times(1)).findStreetsById(any());
    }

    @Test
    void testListStreetsMunich() {
        final Message<ListStrassenDto> listStrassenMsg = createMessageHelper(new ListStrassenDto());
        when(streetsMunichInPort.listStreets(any())).thenReturn(new StrasseResponse());

        messageProcessor.listStreetMunich().accept(listStrassenMsg);

        verify(streetsMunichInPort, times(1)).listStreets(any());
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
