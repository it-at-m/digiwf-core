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

import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        assertThatThrownBy(() -> this.addressClientOutPort.searchAddresses(SearchAddressesGermanyModel.builder().build()))
                .isInstanceOf(BpmnError.class);
    }

    @Test
    void testSearchAddressesThrowsIncidentErrorIfAddressServiceIntegrationServerErrorExceptionOccurs() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationServerErrorException("test", new RuntimeException())).when(addressGermanyApi).searchAddresses(any(SearchAddressesGermanyModel.class));
        assertThatThrownBy(() -> this.addressClientOutPort.searchAddresses(SearchAddressesGermanyModel.builder().build()))
                .isInstanceOf(IncidentError.class);
    }

    @Test
    void testSearchAddressesThrowsIncidentErrorIfAddressServiceIntegrationExceptionOccurs() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationException("test", new RuntimeException())).when(addressGermanyApi).searchAddresses(any(SearchAddressesGermanyModel.class));
        assertThatThrownBy(() -> this.addressClientOutPort.searchAddresses(SearchAddressesGermanyModel.builder().build()))
                .isInstanceOf(IncidentError.class);
    }

    @Test
    void testCheckAddressThrowsBpmnError() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationClientErrorException("test", new RuntimeException())).when(addressMunichApi).checkAddress(any(CheckAddressesModel.class));
        assertThatThrownBy(() -> this.addressClientOutPort.checkAddress(CheckAddressesModel.builder().build()))
                .isInstanceOf(BpmnError.class);
    }

    @Test
    void testCheckAddressThrowsIncidentErrorIfAddressServiceIntegrationServerErrorExceptionOccurs() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationServerErrorException("test", new RuntimeException())).when(addressMunichApi).checkAddress(any(CheckAddressesModel.class));
        assertThatThrownBy(() -> this.addressClientOutPort.checkAddress(CheckAddressesModel.builder().build()))
                .isInstanceOf(IncidentError.class);
    }

    @Test
    void testCheckAddressThrowsIncidentErrorIfAddressServiceIntegrationExceptionOccurs() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationException("test", new RuntimeException())).when(addressMunichApi).checkAddress(any(CheckAddressesModel.class));
        assertThatThrownBy(() -> this.addressClientOutPort.checkAddress(CheckAddressesModel.builder().build()))
                .isInstanceOf(IncidentError.class);
    }

    @Test
    void testListAddressesThrowsBpmnError() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationClientErrorException("test", new RuntimeException())).when(addressMunichApi).listAddresses(any(ListAddressesModel.class));
        assertThatThrownBy(() ->  this.addressClientOutPort.listAddresses(ListAddressesModel.builder().build()))
                .isInstanceOf(BpmnError.class);
    }

    @Test
    void testListAddressesThrowsIncidentErrorIfAddressServiceIntegrationServerErrorExceptionOccurs() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationServerErrorException("test", new RuntimeException())).when(addressMunichApi).listAddresses(any(ListAddressesModel.class));
        assertThatThrownBy(() ->  this.addressClientOutPort.listAddresses(ListAddressesModel.builder().build()))
                .isInstanceOf(IncidentError.class);
    }

    @Test
    void testListAddressesThrowsIncidentErrorIfAddressServiceIntegrationExceptionOccurs() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationException("test", new RuntimeException())).when(addressMunichApi).listAddresses(any(ListAddressesModel.class));
        assertThatThrownBy(() ->  this.addressClientOutPort.listAddresses(ListAddressesModel.builder().build()))
                .isInstanceOf(IncidentError.class);
    }

    @Test
    void testListChangesThrowsBpmnError() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationClientErrorException("test", new RuntimeException())).when(addressMunichApi).listChanges(any(ListAddressChangesModel.class));
        assertThatThrownBy(() ->  this.addressClientOutPort.listChanges(ListAddressChangesModel.builder().build()))
                .isInstanceOf(BpmnError.class);
    }

    @Test
    void testListChangesThrowsIncidentErrorIfAddressServiceIntegrationServerErrorExceptionOccurs() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationServerErrorException("test", new RuntimeException())).when(addressMunichApi).listChanges(any(ListAddressChangesModel.class));
        assertThatThrownBy(() ->  this.addressClientOutPort.listChanges(ListAddressChangesModel.builder().build()))
                .isInstanceOf(IncidentError.class);
    }

    @Test
    void testListChangesThrowsIncidentErrorIfAddressServiceIntegrationExceptionOccurs() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationException("test", new RuntimeException())).when(addressMunichApi).listChanges(any(ListAddressChangesModel.class));
        assertThatThrownBy(() ->  this.addressClientOutPort.listChanges(ListAddressChangesModel.builder().build()))
                .isInstanceOf(IncidentError.class);
    }

    @Test
    void testSearchAddressesMunichThrowsBpmnError() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationClientErrorException("test", new RuntimeException())).when(addressMunichApi).searchAddresses(any(SearchAddressesModel.class));
        assertThatThrownBy(() ->  this.addressClientOutPort.searchAddresses(SearchAddressesModel.builder().build()))
                .isInstanceOf(BpmnError.class);
    }

    @Test
    void testSearchAddressesMunichThrowsIncidentErrorIfAddressServiceIntegrationServerErrorExceptionOccurs() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationServerErrorException("test", new RuntimeException())).when(addressMunichApi).searchAddresses(any(SearchAddressesModel.class));
        assertThatThrownBy(() ->  this.addressClientOutPort.searchAddresses(SearchAddressesModel.builder().build()))
                .isInstanceOf(IncidentError.class);
    }

    @Test
    void testSearchAddressesMunichThrowsIncidentErrorIfAddressServiceIntegrationExceptionOccurs() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationException("test", new RuntimeException())).when(addressMunichApi).searchAddresses(any(SearchAddressesModel.class));
        assertThatThrownBy(() ->  this.addressClientOutPort.searchAddresses(SearchAddressesModel.builder().build()))
                .isInstanceOf(IncidentError.class);
    }

    @Test
    void testSearchAddressesGeoThrowsBpmnError() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationClientErrorException("test", new RuntimeException())).when(addressMunichApi).searchAddressesGeo(any(SearchAddressesGeoModel.class));
        assertThatThrownBy(() ->  this.addressClientOutPort.searchAddressesGeo(SearchAddressesGeoModel.builder().build()))
                .isInstanceOf(BpmnError.class);
    }

    @Test
    void testSearchAddressesGeoThrowsIncidentErrorIfAddressServiceIntegrationServerErrorExceptionOccurs() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationServerErrorException("test", new RuntimeException())).when(addressMunichApi).searchAddressesGeo(any(SearchAddressesGeoModel.class));
        assertThatThrownBy(() ->  this.addressClientOutPort.searchAddressesGeo(SearchAddressesGeoModel.builder().build()))
                .isInstanceOf(IncidentError.class);
    }

    @Test
    void testSearchAddressesGeoThrowsIncidentErrorIfAddressServiceIntegrationExceptionOccurs() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationException("test", new RuntimeException())).when(addressMunichApi).searchAddressesGeo(any(SearchAddressesGeoModel.class));
        assertThatThrownBy(() ->  this.addressClientOutPort.searchAddressesGeo(SearchAddressesGeoModel.builder().build()))
                .isInstanceOf(IncidentError.class);
    }

    @Test
    void testSearchStreetsThrowsBpmnError() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationClientErrorException("test", new RuntimeException())).when(streetsMunichApi).findStreetsById(anyLong());
        assertThatThrownBy(() ->  this.addressClientOutPort.findStreetsById(1L))
                .isInstanceOf(BpmnError.class);
    }

    @Test
    void testSearchStreetsThrowsIncidentErrorIfAddressServiceIntegrationServerErrorExceptionOccurs() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationServerErrorException("test", new RuntimeException())).when(streetsMunichApi).findStreetsById(anyLong());
        assertThatThrownBy(() ->  this.addressClientOutPort.findStreetsById(1L))
                .isInstanceOf(IncidentError.class);
    }

    @Test
    void testSearchStreetsThrowsIncidentErrorIfAddressServiceIntegrationExceptionOccurs() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationException("test", new RuntimeException())).when(streetsMunichApi).findStreetsById(anyLong());
        assertThatThrownBy(() ->  this.addressClientOutPort.findStreetsById(1L))
                .isInstanceOf(IncidentError.class);
    }

    @Test
    void testListStreetsThrowsBpmnError() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationClientErrorException("test", new RuntimeException())).when(streetsMunichApi).listStreets(any(ListStreetsModel.class));
        assertThatThrownBy(() ->  this.addressClientOutPort.listStreets(ListStreetsModel.builder().build()))
                .isInstanceOf(BpmnError.class);
    }

    @Test
    void testListStreetsThrowsIncidentErrorIfAddressServiceIntegrationServerErrorExceptionOccurs() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationServerErrorException("test", new RuntimeException())).when(streetsMunichApi).listStreets(any(ListStreetsModel.class));
        assertThatThrownBy(() ->  this.addressClientOutPort.listStreets(ListStreetsModel.builder().build()))
                .isInstanceOf(IncidentError.class);
    }

    @Test
    void testListStreetsThrowsIncidentErrorIfAddressServiceIntegrationExceptionOccurs() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        doThrow(new AddressServiceIntegrationException("test", new RuntimeException())).when(streetsMunichApi).listStreets(any(ListStreetsModel.class));
        assertThatThrownBy(() ->  this.addressClientOutPort.listStreets(ListStreetsModel.builder().build()))
                .isInstanceOf(IncidentError.class);
    }
}
