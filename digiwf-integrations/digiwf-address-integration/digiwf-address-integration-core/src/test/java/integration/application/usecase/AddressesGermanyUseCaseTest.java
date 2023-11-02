package integration.application.usecase;

import de.muenchen.oss.digiwf.address.integration.application.port.in.AddressGermanyInPort;
import de.muenchen.oss.digiwf.address.integration.application.port.out.AddressClientOutPort;
import de.muenchen.oss.digiwf.address.integration.application.usecase.AddressesGermanyUseCase;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.BundesweiteAdresseResponse;
import de.muenchen.oss.digiwf.address.integration.client.model.request.SearchAddressesGermanyModel;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AddressesGermanyUseCaseTest {

    private final AddressClientOutPort addressClientOutPort = Mockito.mock(AddressClientOutPort.class);

    private final AddressGermanyInPort addressesGermanyUseCase = new AddressesGermanyUseCase(addressClientOutPort);

    @Test
    void testSearchAddresses_returnsBundesweiteAdresseResponse() throws BpmnError, IncidentError {
        SearchAddressesGermanyModel model = SearchAddressesGermanyModel.builder().build();
        BundesweiteAdresseResponse expectedResponse = new BundesweiteAdresseResponse();

        when(addressClientOutPort.searchAddresses(model)).thenReturn(expectedResponse);

        BundesweiteAdresseResponse actualResponse = addressesGermanyUseCase.searchAddresses(model);

        assertEquals(expectedResponse, actualResponse);
        verify(addressClientOutPort).searchAddresses(model);
    }

    @Test
    void testSearchAddresses_throwsBpmnError() throws BpmnError, IncidentError {
        final SearchAddressesGermanyModel model = SearchAddressesGermanyModel.builder().build();
        final BpmnError expectedError = new BpmnError("400", "SomeError");

        when(addressClientOutPort.searchAddresses(model)).thenThrow(expectedError);

        assertThrows(BpmnError.class, () -> {
            addressesGermanyUseCase.searchAddresses(model);
        });
    }

    @Test
    void testSearchAddresses_throwsIncidentError() throws BpmnError, IncidentError {
        final SearchAddressesGermanyModel model = SearchAddressesGermanyModel.builder().build();
        final IncidentError expectedError = new IncidentError("SomeError");

        when(addressClientOutPort.searchAddresses(model)).thenThrow(expectedError);

        assertThrows(IncidentError.class, () -> {
            addressesGermanyUseCase.searchAddresses(model);
        });
    }

}
