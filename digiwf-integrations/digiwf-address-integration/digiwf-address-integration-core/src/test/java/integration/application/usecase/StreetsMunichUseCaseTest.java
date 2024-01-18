package integration.application.usecase;

import de.muenchen.oss.digiwf.address.integration.application.port.in.StreetsMunichInPort;
import de.muenchen.oss.digiwf.address.integration.application.port.out.AddressClientOutPort;
import de.muenchen.oss.digiwf.address.integration.application.usecase.StreetsMunichUseCase;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.Strasse;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.StrasseResponse;
import de.muenchen.oss.digiwf.address.integration.client.model.request.ListStreetsModel;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class StreetsMunichUseCaseTest {

    private final AddressClientOutPort addressClientOutPort = mock(AddressClientOutPort.class);

    private final StreetsMunichInPort streetsMunichUseCase = new StreetsMunichUseCase(addressClientOutPort);

    @Test
    void testFindStreetsById_returnsStrasse() throws BpmnError, IncidentError {
        long streetId = 0L;
        final Strasse expectedResponse = new Strasse();

        when(addressClientOutPort.findStreetsById(streetId)).thenReturn(expectedResponse);

        final Strasse actualResponse = streetsMunichUseCase.findStreetsById(streetId);

        assertThat(actualResponse).isEqualTo(expectedResponse);
        verify(addressClientOutPort).findStreetsById(streetId);
    }

    @Test
    void testFindStreetsById_throwsBpmnError() throws BpmnError, IncidentError {
        final long streetId = 0L;
        final BpmnError expectedError = new BpmnError("400", "SomeError");

        when(addressClientOutPort.findStreetsById(streetId)).thenThrow(expectedError);

        assertThatThrownBy(() -> streetsMunichUseCase.findStreetsById(streetId))
                .isInstanceOf(BpmnError.class)
                .isEqualTo(expectedError);
    }

    @Test
    void testFindStreetsById_throwsIncidentError() throws BpmnError, IncidentError {
        final long streetId = 0L;
        final IncidentError expectedError = new IncidentError("SomeError");

        when(addressClientOutPort.findStreetsById(streetId)).thenThrow(expectedError);

        assertThatThrownBy(() -> streetsMunichUseCase.findStreetsById(streetId))
                .isInstanceOf(IncidentError.class)
                .isEqualTo(expectedError);
    }

    @Test
    void testListStreets_returnsStrasseResponse() throws BpmnError, IncidentError {
        final ListStreetsModel listStreetsModel = ListStreetsModel.builder().build();
        final StrasseResponse expectedResponse = new StrasseResponse();

        when(addressClientOutPort.listStreets(listStreetsModel)).thenReturn(expectedResponse);

        final StrasseResponse actualResponse = streetsMunichUseCase.listStreets(listStreetsModel);

        assertThat(actualResponse).isEqualTo(expectedResponse);
        verify(addressClientOutPort).listStreets(listStreetsModel);
    }

    @Test
    void testListStreets_throwsBpmnError() throws BpmnError, IncidentError {
        final ListStreetsModel listStreetsModel = ListStreetsModel.builder().build();
        final BpmnError expectedError = new BpmnError("400", "SomeError");

        when(addressClientOutPort.listStreets(listStreetsModel)).thenThrow(expectedError);

        assertThatThrownBy(() -> streetsMunichUseCase.listStreets(listStreetsModel))
                .isInstanceOf(BpmnError.class)
                .isEqualTo(expectedError);
    }

    @Test
    void testListStreets_throwsIncidentError() throws BpmnError, IncidentError {
        final ListStreetsModel listStreetsModel = ListStreetsModel.builder().build();
        final IncidentError expectedError = new IncidentError("SomeError");

        when(addressClientOutPort.listStreets(listStreetsModel)).thenThrow(expectedError);

        assertThatThrownBy(() -> streetsMunichUseCase.listStreets(listStreetsModel))
                .isInstanceOf(IncidentError.class)
                .isEqualTo(expectedError);
    }

}
