package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.out.DmsUserPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.ReadMetadataPort;
import de.muenchen.oss.digiwf.dms.integration.domain.Metadata;
import de.muenchen.oss.digiwf.dms.integration.domain.ObjectType;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class ReadMetadataServiceTest {

    private final ReadMetadataPort readMetadataPort = mock(ReadMetadataPort.class);
    private final DmsUserPort dmsUserPort = mock(DmsUserPort.class);

    private final  ReadMetadataService readMetadataService = new ReadMetadataService(readMetadataPort, dmsUserPort);

    @Test
    void readMetadata() {
        when(this.readMetadataPort.readMetadata(any(), any())).thenReturn(new Metadata("name","Sachakte","url"));
        when(this.dmsUserPort.getDmsUser()).thenReturn("user");

        readMetadataService.readMetadata(ObjectType.Sachakte,"coo");

        verify(this.dmsUserPort, times(1)).getDmsUser();
        verify(this.readMetadataPort, times(1)).readMetadata("coo","user");
    }

    @Test
    void readContentMetadata() {
        when(this.readMetadataPort.readContentMetadata(any(), any())).thenReturn(new Metadata("name","type","url"));
        when(this.dmsUserPort.getDmsUser()).thenReturn("user");

        readMetadataService.readMetadata(ObjectType.Schriftstueck,"coo");

        verify(this.dmsUserPort, times(1)).getDmsUser();
        verify(this.readMetadataPort, times(1)).readContentMetadata("coo","user");
    }

    @Test
    void readMetadataThrowsBpmnError() {
        when(this.readMetadataPort.readMetadata(any(), any())).thenReturn(new Metadata("name","Ausgang","url"));
        when(this.dmsUserPort.getDmsUser()).thenReturn("user");

        BpmnError bpmnError = catchThrowableOfType(() -> readMetadataService.readMetadata(ObjectType.Sachakte,"coo"), BpmnError.class);

        String expectedMessage = "Das übergebene Objekt mit der COO-Adresse " + "coo" + " ist ungültig, da das übergebene Objekt von der Objektklasse " + "Ausgang" + " ist und dies nicht mit der/den erwarteten Objektklasse/n " + "Sachakte" + " übereinstimmt.";
        String actualMessage = bpmnError.getErrorMessage();

        assertThat(actualMessage).isEqualTo(expectedMessage);

        assertThat(bpmnError.getErrorCode()).isEqualTo("AUFRUF_OBJEKT_FALSCHER_FEHLERKLASSE");

        verify(this.dmsUserPort, times(1)).getDmsUser();
        verify(this.readMetadataPort, times(1)).readMetadata("coo","user");
    }
}