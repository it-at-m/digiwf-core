package de.muenchen.oss.digiwf.dms.integration.adapter.out.fabasoft;

import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;

public class DMSErrorHandler {
    public void handleError(int code, String errorMessage) {

        final DMSStatusCode statusCode = DMSStatusCode.byCode(code);

        if (statusCode == DMSStatusCode.UNBEKANNTER_FEHLER) {
            throw new IncidentError(errorMessage);
        } else if (statusCode != DMSStatusCode.UEBERTRAGUNG_ERFORLGREICH){
            throw new BpmnError(statusCode.toString(), errorMessage);
        }
    }
}
