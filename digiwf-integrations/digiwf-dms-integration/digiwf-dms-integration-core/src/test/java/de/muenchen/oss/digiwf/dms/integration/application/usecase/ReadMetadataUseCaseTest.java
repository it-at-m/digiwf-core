package de.muenchen.oss.digiwf.dms.integration.application.usecase;

import de.muenchen.oss.digiwf.dms.integration.application.port.out.DmsUserOutPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.ReadMetadataOutPort;
import de.muenchen.oss.digiwf.dms.integration.domain.Metadata;
import de.muenchen.oss.digiwf.dms.integration.domain.ObjectType;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.Mockito.*;

class ReadMetadataUseCaseTest {

    private final ReadMetadataOutPort readMetadataOutPort = mock(ReadMetadataOutPort.class);
    private final DmsUserOutPort dmsUserOutPort = mock(DmsUserOutPort.class);

    private final ReadMetadataUseCase readMetadataUseCase = new ReadMetadataUseCase(readMetadataOutPort, dmsUserOutPort);

    @Test
    void readMetadata() {
        when(this.readMetadataOutPort.readMetadata(any(), any())).thenReturn(new Metadata("name", "Sachakte", "url"));
        when(this.dmsUserOutPort.getDmsUser()).thenReturn("user");

        readMetadataUseCase.readMetadata(ObjectType.Sachakte, "coo");

        verify(this.dmsUserOutPort, times(1)).getDmsUser();
        verify(this.readMetadataOutPort, times(1)).readMetadata("coo", "user");
    }

    @Test
    void readContentMetadata() {
        when(this.readMetadataOutPort.readContentMetadata(any(), any())).thenReturn(new Metadata("name", "type", "url"));
        when(this.dmsUserOutPort.getDmsUser()).thenReturn("user");

        readMetadataUseCase.readMetadata(ObjectType.Schriftstueck, "coo");

        verify(this.dmsUserOutPort, times(1)).getDmsUser();
        verify(this.readMetadataOutPort, times(1)).readContentMetadata("coo", "user");
    }

    @Test
    void readMetadataThrowsBpmnError() {
        when(this.readMetadataOutPort.readMetadata(any(), any())).thenReturn(new Metadata("name", "Ausgang", "url"));
        when(this.dmsUserOutPort.getDmsUser()).thenReturn("user");

        BpmnError bpmnError = catchThrowableOfType(() -> readMetadataUseCase.readMetadata(ObjectType.Sachakte, "coo"), BpmnError.class);

        String expectedMessage = "Das übergebene Objekt mit der COO-Adresse " + "coo" + " ist ungültig, da das übergebene Objekt von der Objektklasse " + "Ausgang" + " ist und dies nicht mit der/den erwarteten Objektklasse/n " + "Sachakte" + " übereinstimmt.";
        String actualMessage = bpmnError.getErrorMessage();

        assertThat(actualMessage).isEqualTo(expectedMessage);

        assertThat(bpmnError.getErrorCode()).isEqualTo("AUFRUF_OBJEKT_FALSCHER_FEHLERKLASSE");

        verify(this.dmsUserOutPort, times(1)).getDmsUser();
        verify(this.readMetadataOutPort, times(1)).readMetadata("coo", "user");
    }
}