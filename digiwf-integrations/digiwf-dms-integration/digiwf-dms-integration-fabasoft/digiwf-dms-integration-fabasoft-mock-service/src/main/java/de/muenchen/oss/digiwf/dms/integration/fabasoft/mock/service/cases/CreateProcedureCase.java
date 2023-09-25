package de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.service.cases;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateProcedureGI;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateProcedureGIResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.stereotype.Component;

import static de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.MockUtils.stubOperation;

@Component
public class CreateProcedureCase implements MockCase {

    @Override
    public void initCase(WireMockServer server) {

        stubOperation(
                server,
                "CreateProcedureGI",
                CreateProcedureGI.class, (u) -> true,
                new CreateProcedureGIResponse());

    }

}
