package de.muenchen.oss.digiwf.dms.integration.adapter.out.fabasoft;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateProcedureGI;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateProcedureGIResponse;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.LHMBAI151700GIWSDSoap;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.ProcedureRepository;
import de.muenchen.oss.digiwf.dms.integration.domain.Procedure;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class FabasoftAdapter implements ProcedureRepository {

    private final FabasoftProperties properties;
    private final LHMBAI151700GIWSDSoap wsClient;

    @Override
    public Procedure createProcedure(Procedure procedure, String user) {
        log.info("calling CreateProcedureGI: " + procedure.toString());

        final CreateProcedureGI request = new CreateProcedureGI();
        request.setUserlogin(user);
        request.setReferrednumber(procedure.getFileCOO());
        request.setBusinessapp(this.properties.getBusinessapp());
        request.setShortname(procedure.getTitle());
        request.setFilesubj(procedure.getTitle());
        request.setFiletype("Elektronisch");

        final CreateProcedureGIResponse response = this.wsClient.createProcedureGI(request);

        final DMSStatusCode statusCode = DMSStatusCode.byCode(response.getStatus());
        if (statusCode != DMSStatusCode.UEBERTRAGUNG_ERFORLGREICH) {
            throw new IncidentError(response.getErrormessage());
        }

        return new Procedure(response.getObjid(), procedure.getFileCOO(), procedure.getTitle());
    }

}
