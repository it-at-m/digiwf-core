package integration.adapter.out;

import de.muenchen.oss.digiwf.address.integration.adapter.out.AddressClientOutAdapter;
import de.muenchen.oss.digiwf.address.integration.application.port.out.AddressClientOutPort;
import de.muenchen.oss.digiwf.address.integration.client.api.AddressGermanyApi;
import de.muenchen.oss.digiwf.address.integration.client.api.AddressMunichApi;
import de.muenchen.oss.digiwf.address.integration.client.api.StreetsMunichApi;
import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationClientErrorException;
import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationException;
import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationServerErrorException;
import de.muenchen.oss.digiwf.address.integration.client.model.request.*;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AddressClientOutAdapterTest {

    private final AddressGermanyApi addressGermanyApi = mock(AddressGermanyApi.class);
    private final AddressMunichApi addressMunichApi = mock(AddressMunichApi.class);
    private final StreetsMunichApi streetsMunichApi = mock(StreetsMunichApi.class);

    private final AddressClientOutPort addressClientOutPort = new AddressClientOutAdapter(addressGermanyApi, addressMunichApi, streetsMunichApi);

    @AfterEach
    void setup() {
        reset(addressGermanyApi);
        reset(addressMunichApi);
        reset(streetsMunichApi);
    }

    @Test
    void testSearchAddressesThrowsBpmnError() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationClientErrorException("test", new RuntimeException())).when(addressGermanyApi).searchAddresses(any(SearchAddressesGermanyModel.class));
        assertThrows(BpmnError.class, () -> this.addressClientOutPort.searchAddresses(SearchAddressesGermanyModel.builder().build()));
    }

    @Test
    void testSearchAddressesThrowsIncidentErrorIfAddressServiceIntegrationServerErrorExceptionOccurs() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationServerErrorException("test", new RuntimeException())).when(addressGermanyApi).searchAddresses(any(SearchAddressesGermanyModel.class));
        assertThrows(IncidentError.class, () -> this.addressClientOutPort.searchAddresses(SearchAddressesGermanyModel.builder().build()));
    }

    @Test
    void testSearchAddressesThrowsIncidentErrorIfAddressServiceIntegrationExceptionOccurs() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationException("test", new RuntimeException())).when(addressGermanyApi).searchAddresses(any(SearchAddressesGermanyModel.class));
        assertThrows(IncidentError.class, () -> this.addressClientOutPort.searchAddresses(SearchAddressesGermanyModel.builder().build()));
    }

    @Test
    void testCheckAddressThrowsBpmnError() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationClientErrorException("test", new RuntimeException())).when(addressMunichApi).checkAddress(any(CheckAddressesModel.class));
        assertThrows(BpmnError.class, () -> this.addressClientOutPort.checkAddress(CheckAddressesModel.builder().build()));
    }

    @Test
    void testCheckAddressThrowsIncidentErrorIfAddressServiceIntegrationServerErrorExceptionOccurs() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationServerErrorException("test", new RuntimeException())).when(addressMunichApi).checkAddress(any(CheckAddressesModel.class));
        assertThrows(IncidentError.class, () -> this.addressClientOutPort.checkAddress(CheckAddressesModel.builder().build()));
    }

    @Test
    void testCheckAddressThrowsIncidentErrorIfAddressServiceIntegrationExceptionOccurs() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationException("test", new RuntimeException())).when(addressMunichApi).checkAddress(any(CheckAddressesModel.class));
        assertThrows(IncidentError.class, () -> this.addressClientOutPort.checkAddress(CheckAddressesModel.builder().build()));
    }

    @Test
    void testListAddressesThrowsBpmnError() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationClientErrorException("test", new RuntimeException())).when(addressMunichApi).listAddresses(any(ListAddressesModel.class));
        assertThrows(BpmnError.class, () -> this.addressClientOutPort.listAddresses(ListAddressesModel.builder().build()));
    }

    @Test
    void testListAddressesThrowsIncidentErrorIfAddressServiceIntegrationServerErrorExceptionOccurs() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationServerErrorException("test", new RuntimeException())).when(addressMunichApi).listAddresses(any(ListAddressesModel.class));
        assertThrows(IncidentError.class, () -> this.addressClientOutPort.listAddresses(ListAddressesModel.builder().build()));
    }

    @Test
    void testListAddressesThrowsIncidentErrorIfAddressServiceIntegrationExceptionOccurs() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationException("test", new RuntimeException())).when(addressMunichApi).listAddresses(any(ListAddressesModel.class));
        assertThrows(IncidentError.class, () -> this.addressClientOutPort.listAddresses(ListAddressesModel.builder().build()));
    }

    @Test
    void testListChangesThrowsBpmnError() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationClientErrorException("test", new RuntimeException())).when(addressMunichApi).listChanges(any(ListAddressChangesModel.class));
        assertThrows(BpmnError.class, () -> this.addressClientOutPort.listChanges(ListAddressChangesModel.builder().build()));
    }

    @Test
    void testListChangesThrowsIncidentErrorIfAddressServiceIntegrationServerErrorExceptionOccurs() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationServerErrorException("test", new RuntimeException())).when(addressMunichApi).listChanges(any(ListAddressChangesModel.class));
        assertThrows(IncidentError.class, () -> this.addressClientOutPort.listChanges(ListAddressChangesModel.builder().build()));
    }

    @Test
    void testListChangesThrowsIncidentErrorIfAddressServiceIntegrationExceptionOccurs() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationException("test", new RuntimeException())).when(addressMunichApi).listChanges(any(ListAddressChangesModel.class));
        assertThrows(IncidentError.class, () -> this.addressClientOutPort.listChanges(ListAddressChangesModel.builder().build()));
    }

    @Test
    void testSearchAddressesMunichThrowsBpmnError() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationClientErrorException("test", new RuntimeException())).when(addressMunichApi).searchAddresses(any(SearchAddressesModel.class));
        assertThrows(BpmnError.class, () -> this.addressClientOutPort.searchAddresses(SearchAddressesModel.builder().build()));
    }

    @Test
    void testSearchAddressesMunichThrowsIncidentErrorIfAddressServiceIntegrationServerErrorExceptionOccurs() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationServerErrorException("test", new RuntimeException())).when(addressMunichApi).searchAddresses(any(SearchAddressesModel.class));
        assertThrows(IncidentError.class, () -> this.addressClientOutPort.searchAddresses(SearchAddressesModel.builder().build()));
    }

    @Test
    void testSearchAddressesMunichThrowsIncidentErrorIfAddressServiceIntegrationExceptionOccurs() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationException("test", new RuntimeException())).when(addressMunichApi).searchAddresses(any(SearchAddressesModel.class));
        assertThrows(IncidentError.class, () -> this.addressClientOutPort.searchAddresses(SearchAddressesModel.builder().build()));
    }

    @Test
    void testSearchAddressesGeoThrowsBpmnError() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationClientErrorException("test", new RuntimeException())).when(addressMunichApi).searchAddressesGeo(any(SearchAddressesGeoModel.class));
        assertThrows(BpmnError.class, () -> this.addressClientOutPort.searchAddressesGeo(SearchAddressesGeoModel.builder().build()));
    }

    @Test
    void testSearchAddressesGeoThrowsIncidentErrorIfAddressServiceIntegrationServerErrorExceptionOccurs() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationServerErrorException("test", new RuntimeException())).when(addressMunichApi).searchAddressesGeo(any(SearchAddressesGeoModel.class));
        assertThrows(IncidentError.class, () -> this.addressClientOutPort.searchAddressesGeo(SearchAddressesGeoModel.builder().build()));
    }

    @Test
    void testSearchAddressesGeoThrowsIncidentErrorIfAddressServiceIntegrationExceptionOccurs() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationException("test", new RuntimeException())).when(addressMunichApi).searchAddressesGeo(any(SearchAddressesGeoModel.class));
        assertThrows(IncidentError.class, () -> this.addressClientOutPort.searchAddressesGeo(SearchAddressesGeoModel.builder().build()));
    }

    @Test
    void testSearchStreetsThrowsBpmnError() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationClientErrorException("test", new RuntimeException())).when(streetsMunichApi).findStreetsById(anyLong());
        assertThrows(BpmnError.class, () -> this.addressClientOutPort.findStreetsById(1L));
    }

    @Test
    void testSearchStreetsThrowsIncidentErrorIfAddressServiceIntegrationServerErrorExceptionOccurs() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationServerErrorException("test", new RuntimeException())).when(streetsMunichApi).findStreetsById(anyLong());
        assertThrows(IncidentError.class, () -> this.addressClientOutPort.findStreetsById(1L));
    }

    @Test
    void testSearchStreetsThrowsIncidentErrorIfAddressServiceIntegrationExceptionOccurs() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationException("test", new RuntimeException())).when(streetsMunichApi).findStreetsById(anyLong());
        assertThrows(IncidentError.class, () -> this.addressClientOutPort.findStreetsById(1L));
    }

    @Test
    void testListStreetsThrowsBpmnError() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationClientErrorException("test", new RuntimeException())).when(streetsMunichApi).listStreets(any(ListStreetsModel.class));
        assertThrows(BpmnError.class, () -> this.addressClientOutPort.listStreets(ListStreetsModel.builder().build()));
    }

    @Test
    void testListStreetsThrowsIncidentErrorIfAddressServiceIntegrationServerErrorExceptionOccurs() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationServerErrorException("test", new RuntimeException())).when(streetsMunichApi).listStreets(any(ListStreetsModel.class));
        assertThrows(IncidentError.class, () -> this.addressClientOutPort.listStreets(ListStreetsModel.builder().build()));
    }

    @Test
    void testListStreetsThrowsIncidentErrorIfAddressServiceIntegrationExceptionOccurs() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationException("test", new RuntimeException())).when(streetsMunichApi).listStreets(any(ListStreetsModel.class));
        assertThrows(IncidentError.class, () -> this.addressClientOutPort.listStreets(ListStreetsModel.builder().build()));
    }
}
