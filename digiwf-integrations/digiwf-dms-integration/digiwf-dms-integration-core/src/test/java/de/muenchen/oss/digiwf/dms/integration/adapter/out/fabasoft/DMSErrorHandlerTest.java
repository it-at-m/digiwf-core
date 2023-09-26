package de.muenchen.oss.digiwf.dms.integration.adapter.out.fabasoft;

import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DMSErrorHandlerTest {

    private DMSErrorHandler dmsErrorHandler;

    @BeforeEach
    void setup() {
        dmsErrorHandler = new DMSErrorHandler();
    }

    @Test
    void handleIncident() {

        int errorCode = -1;
        String errorMessage = "Unbekannter Fehler";

        IncidentError incidentError = assertThrows(IncidentError.class, () -> this.dmsErrorHandler.handleError(errorCode, errorMessage));

        String actualMessage = incidentError.getErrorMessage();

        assertEquals(errorMessage, actualMessage);

    }

    @Test
    void handleBPMNError() {

        int errorCode = 2;
        String errorMessage = "Fehlende Berechtigung";

        BpmnError bpmnError = assertThrows(BpmnError.class, () -> this.dmsErrorHandler.handleError(errorCode, errorMessage));

        String actualMessage = bpmnError.getErrorMessage();

        assertEquals(errorMessage, actualMessage);

        assertEquals("FEHLENDE_BERECHTIGUNG",bpmnError.getErrorCode());

    }

    @Test
    void uebertragungErfolgreich() {

        int code = 0;

        this.dmsErrorHandler.handleError(code, null);

    }
}