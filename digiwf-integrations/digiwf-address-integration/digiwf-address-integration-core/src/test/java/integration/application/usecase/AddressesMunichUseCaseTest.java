package integration.application.usecase;

import de.muenchen.oss.digiwf.address.integration.application.port.in.AddressMunichInPort;
import de.muenchen.oss.digiwf.address.integration.application.port.out.AddressClientOutPort;
import de.muenchen.oss.digiwf.address.integration.application.usecase.AddressesMunichUseCase;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.AenderungResponse;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.MuenchenAdresse;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.MuenchenAdresseResponse;
import de.muenchen.oss.digiwf.address.integration.client.model.request.*;
import de.muenchen.oss.digiwf.address.integration.client.model.response.AddressDistancesModel;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class AddressesMunichUseCaseTest {

    private final AddressClientOutPort addressClientOutPort = mock(AddressClientOutPort.class);

    private final AddressMunichInPort addressesMunichUseCase = new AddressesMunichUseCase(addressClientOutPort);

    @Test
    void testCheckAddress_returnsMuenchenAdresse() {
        final CheckAddressesModel checkAddressesModel = CheckAddressesModel.builder().build();
        final MuenchenAdresse expectedResponse = new MuenchenAdresse();

        when(addressClientOutPort.checkAddress(checkAddressesModel)).thenReturn(expectedResponse);

        final MuenchenAdresse actualResponse = addressesMunichUseCase.checkAddress(checkAddressesModel);

        assertThat(actualResponse).isEqualTo(expectedResponse);
        verify(addressClientOutPort).checkAddress(checkAddressesModel);
    }

    @Test
    void testCheckAddress_throwsBPMNError() {
        final CheckAddressesModel checkAddressesModel = CheckAddressesModel.builder().build();
        final BpmnError expectedError = new BpmnError("400", "SomeError");

        when(addressClientOutPort.checkAddress(checkAddressesModel)).thenThrow(expectedError);

        assertThatThrownBy(() -> addressesMunichUseCase.checkAddress(checkAddressesModel))
                .isInstanceOf(BpmnError.class)
                .isEqualTo(expectedError);
    }

    @Test
    void testCheckAddress_throwsIncidentError() {
        final CheckAddressesModel checkAddressesModel = CheckAddressesModel.builder().build();
        final IncidentError expectedError = new IncidentError("SomeError");

        when(addressClientOutPort.checkAddress(checkAddressesModel)).thenThrow(expectedError);

        assertThatThrownBy(() -> addressesMunichUseCase.checkAddress(checkAddressesModel))
                .isInstanceOf(IncidentError.class)
                .isEqualTo(expectedError);
    }

    @Test
    void testListAddresses_returnsMuenchenAdresseResponse() throws BpmnError, IncidentError {
        final ListAddressesModel listAddressesModel = ListAddressesModel.builder().build();
        final MuenchenAdresseResponse expectedResponse = new MuenchenAdresseResponse();

        when(addressClientOutPort.listAddresses(listAddressesModel)).thenReturn(expectedResponse);

        final MuenchenAdresseResponse actualResponse = addressesMunichUseCase.listAddresses(listAddressesModel);

        assertThat(actualResponse).isEqualTo(expectedResponse);
        verify(addressClientOutPort).listAddresses(listAddressesModel);
    }

    @Test
    void testListAddresses_throwsBpmnError() throws BpmnError, IncidentError {
        final ListAddressesModel listAddressesModel = ListAddressesModel.builder().build();
        final BpmnError expectedError = new BpmnError("400", "SomeError");

        when(addressClientOutPort.listAddresses(listAddressesModel)).thenThrow(expectedError);

        assertThatThrownBy(() -> addressesMunichUseCase.listAddresses(listAddressesModel))
                .isInstanceOf(BpmnError.class)
                .isEqualTo(expectedError);
    }

    @Test
    void testListAddresses_throwsIncidentError() throws BpmnError, IncidentError {
        final ListAddressesModel listAddressesModel = ListAddressesModel.builder().build();
        final IncidentError expectedError = new IncidentError("SomeError");

        when(addressClientOutPort.listAddresses(listAddressesModel)).thenThrow(expectedError);

        assertThatThrownBy(() -> addressesMunichUseCase.listAddresses(listAddressesModel))
                .isInstanceOf(IncidentError.class)
                .isEqualTo(expectedError);
    }

    @Test
    void testListChanges_returnsAenderungResponse() throws BpmnError, IncidentError {
        final ListAddressChangesModel listAddressChangesModel = ListAddressChangesModel.builder().build();
        final AenderungResponse expectedResponse = new AenderungResponse();

        when(addressClientOutPort.listChanges(listAddressChangesModel)).thenReturn(expectedResponse);

        final AenderungResponse actualResponse = addressesMunichUseCase.listChanges(listAddressChangesModel);

        assertThat(actualResponse).isEqualTo(expectedResponse);
        verify(addressClientOutPort).listChanges(listAddressChangesModel);
    }

    @Test
    void testListChanges_throwsBpmnError() throws BpmnError, IncidentError {
        final ListAddressChangesModel listAddressChangesModel = ListAddressChangesModel.builder().build();
        final BpmnError expectedError = new BpmnError("400", "SomeError");

        when(addressClientOutPort.listChanges(listAddressChangesModel)).thenThrow(expectedError);

        assertThatThrownBy(() -> addressesMunichUseCase.listChanges(listAddressChangesModel))
                .isInstanceOf(BpmnError.class)
                .isEqualTo(expectedError);
    }

    @Test
    void testListChanges_throwsIncidentError() throws BpmnError, IncidentError {
        final ListAddressChangesModel listAddressChangesModel = ListAddressChangesModel.builder().build();
        final IncidentError expectedError = new IncidentError("SomeError");

        when(addressClientOutPort.listChanges(listAddressChangesModel)).thenThrow(expectedError);

        assertThatThrownBy(() ->  addressesMunichUseCase.listChanges(listAddressChangesModel))
                .isInstanceOf(IncidentError.class)
                .isEqualTo(expectedError);
    }

    @Test
    void testSearchAddresses_returnsMuenchenAdresseResponse() throws BpmnError, IncidentError {
        final SearchAddressesModel searchAddressesModel = SearchAddressesModel.builder().build();
        final MuenchenAdresseResponse expectedResponse = new MuenchenAdresseResponse();

        when(addressClientOutPort.searchAddresses(searchAddressesModel)).thenReturn(expectedResponse);

        final MuenchenAdresseResponse actualResponse = addressesMunichUseCase.searchAddresses(searchAddressesModel);

        assertThat(actualResponse).isEqualTo(expectedResponse);
        verify(addressClientOutPort).searchAddresses(searchAddressesModel);
    }

    @Test
    void testSearchAddresses_throwsBpmnError() throws BpmnError, IncidentError {
        final SearchAddressesModel searchAddressesModel = SearchAddressesModel.builder().build();
        final BpmnError expectedError = new BpmnError("400", "SomeError");

        when(addressClientOutPort.searchAddresses(searchAddressesModel)).thenThrow(expectedError);

        assertThatThrownBy(() -> addressesMunichUseCase.searchAddresses(searchAddressesModel))
                .isInstanceOf(BpmnError.class)
                .isEqualTo(expectedError);
    }

    @Test
    void testSearchAddresses_throwsIncidentError() throws BpmnError, IncidentError {
        final SearchAddressesModel searchAddressesModel = SearchAddressesModel.builder().build();
        final IncidentError expectedError = new IncidentError("SomeError");

        when(addressClientOutPort.searchAddresses(searchAddressesModel)).thenThrow(expectedError);

        assertThatThrownBy(() ->  addressesMunichUseCase.searchAddresses(searchAddressesModel))
                .isInstanceOf(IncidentError.class)
                .isEqualTo(expectedError);
    }

    @Test
    void testSearchAddressesGeo_returnsAddressDistancesModel() throws BpmnError, IncidentError {
        final SearchAddressesGeoModel searchAddressesGeoModel = SearchAddressesGeoModel.builder().build();
        final AddressDistancesModel expectedResponse = AddressDistancesModel.builder().build();

        when(addressClientOutPort.searchAddressesGeo(searchAddressesGeoModel)).thenReturn(expectedResponse);

        final AddressDistancesModel actualResponse = addressesMunichUseCase.searchAddressesGeo(searchAddressesGeoModel);

        assertThat(actualResponse).isEqualTo(expectedResponse);
        verify(addressClientOutPort).searchAddressesGeo(searchAddressesGeoModel);
    }

    @Test
    void testSearchAddressesGeo_throwsBpmnError() throws BpmnError, IncidentError {
        final SearchAddressesGeoModel searchAddressesGeoModel = SearchAddressesGeoModel.builder().build();
        final BpmnError expectedError = new BpmnError("400", "SomeError");

        when(addressClientOutPort.searchAddressesGeo(searchAddressesGeoModel)).thenThrow(expectedError);

        assertThatThrownBy(() -> addressesMunichUseCase.searchAddressesGeo(searchAddressesGeoModel))
                .isInstanceOf(BpmnError.class)
                .isEqualTo(expectedError);
    }

    @Test
    void testSearchAddressesGeo_throwsIncidentError() throws BpmnError, IncidentError {
        final SearchAddressesGeoModel searchAddressesGeoModel = SearchAddressesGeoModel.builder().build();
        final IncidentError expectedError = new IncidentError("SomeError");

        when(addressClientOutPort.searchAddressesGeo(searchAddressesGeoModel)).thenThrow(expectedError);

        assertThatThrownBy(() ->  addressesMunichUseCase.searchAddressesGeo(searchAddressesGeoModel))
                .isInstanceOf(IncidentError.class)
                .isEqualTo(expectedError);
    }

}
