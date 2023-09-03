package de.muenchen.oss.digiwf.dms.integration.adapter.out.fabasoft;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateProcedureGI;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateProcedureGIResponse;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.LHMBAI151700GIWSDSoap;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.VorgangRepository;
import de.muenchen.oss.digiwf.dms.integration.domain.Vorgang;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class FabasoftAdapter implements VorgangRepository {

    private final FabasoftProperties properties;
    private final LHMBAI151700GIWSDSoap wsClient;

    @Override
    public Vorgang createVorgang(Vorgang vorgang, String user) {
        log.info("calling CreateProcedureGI: " + vorgang.toString());

        final CreateProcedureGI request = new CreateProcedureGI();
        request.setUserlogin(user);
        request.setReferrednumber(vorgang.getSachakteCoo());
        request.setBusinessapp(this.properties.getBusinessapp());
        request.setShortname(vorgang.getTitle());
        request.setFilesubj(vorgang.getTitle());
        request.setFiletype(vorgang.getArt().toString());

        final CreateProcedureGIResponse response = this.wsClient.createProcedureGI(request);

        final DMSStatusCode statusCode = DMSStatusCode.byCode(response.getStatus());
        if (statusCode != DMSStatusCode.UEBERTRAGUNG_ERFORLGREICH) {
            throw new IncidentError(response.getErrormessage());
        }

        return new Vorgang(response.getObjid(), vorgang.getSachakteCoo(), vorgang.getTitle(), vorgang.getArt());
    }

}
